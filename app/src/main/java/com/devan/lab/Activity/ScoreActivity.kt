package com.devan.lab.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.devan.lab.Models.Course
import com.devan.lab.R
import com.devan.lab.Utils.ToastManager
import com.devan.lab.Utils.ToastType
import com.devan.lab.Utils.performClickAnimation
import com.devan.lab.databinding.ActivityScoreBinding
import com.devan.lab.service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import java.io.ByteArrayOutputStream
import java.io.File

class ScoreActivity : AppCompatActivity() {

    private var score: Int = 0
    private lateinit var binding: ActivityScoreBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var firebaseService: FirebaseService
    private var courseId: Int = 0
    private var moduleId: Int = 0
    private var email: String? = null
    private var username: String? = null
    private var course: Course? = null
    private var userAnswers: Map<Int, String> = mapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<View>(R.id.generatePdf).setOnClickListener {
            performClickAnimation(findViewById(R.id.generatePdf))
            createPdfWithIText()
        }

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        firebaseService = FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())

        username = prefs.getString("username", null)

        email = prefs.getString("email", null)
        courseId = intent.getIntExtra("course_id", 0)
        fetchCourse(courseId.toString())
        moduleId = intent.getIntExtra("module_id", -1)
        score = intent.getIntExtra("Score", 0)
        userAnswers = (intent.getSerializableExtra("userAnswers") as HashMap<String, String>).mapKeys { it.key.toInt() }

        if (score > 3) {
            ToastManager.showToast("Congrats. Module Completed!", this, ToastType.SUCCESS)
            findViewById<View>(R.id.generatePdf).visibility = View.VISIBLE
            markModuleAsCompleted()
        } else {
            findViewById<View>(R.id.generatePdf).visibility = View.GONE

            ToastManager.showToast("Quiz Failed. Try again!", this, ToastType.INFO)
        }

        binding.apply {
            scoreResult.text = "$score/5"

            backToHome.setOnClickListener {
                performClickAnimation(backToHome)
                val intent = Intent(this@ScoreActivity, HomeActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun fetchCourse(id: String) {
        firebaseService.getCourseById(id) { course, error ->
            if (course != null) {
                this.course = course
            }
        }
    }

    private fun markModuleAsCompleted() {
        email?.let {
            firebaseService.getUserProgress(it, courseId) { userProgress, error ->
                if (userProgress != null) {
                    val updatedCompletedModules = userProgress.completedModules.toMutableList()
                    if (!updatedCompletedModules.contains(moduleId)) {
                        updatedCompletedModules.add(moduleId)
                        val newPercentage = (updatedCompletedModules.size.toDouble() / course!!.modules.size) * 100
                        val updatedUserAnswers = userAnswers.mapKeys { it.key.toString() }
                        val updatedUserProgress = userProgress.copy(
                            completedModules = updatedCompletedModules,
                            percentage = newPercentage,
                            userAnswers = updatedUserAnswers,
                            score = score
                        )
                        firebaseService.updateUserProgress(it, updatedUserProgress) { success, _ ->
                            if (success) {
                                ToastManager.showToast("Module progress updated.", this, ToastType.SUCCESS)
                            } else {
                                ToastManager.showToast("Failed to update progress.", this, ToastType.ERROR)
                            }
                        }
                    }
                }
            }
        }
    }
    private fun createPdfWithIText() {
        if (course == null) {
            ToastManager.showToast("Course data not available", this, ToastType.ERROR)
            return
        }


        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bannerdark)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val imageData = ImageDataFactory.create(stream.toByteArray())
        val image = Image(imageData)

        val moduleName = course!!.modules.find { it.id == moduleId }?.title ?: "Unknown Module"


        val pdfPath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/$moduleName.pdf"
        val file = File(pdfPath)
        val writer = PdfWriter(file)
        val pdfDoc = com.itextpdf.kernel.pdf.PdfDocument(writer)
        val document = Document(pdfDoc, PageSize.A4)

        image.scaleToFit(PageSize.A4.width - document.leftMargin - document.rightMargin, PageSize.A4.height / 4)
        image.setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.CENTER)
        document.add(image)


        val titleFont = com.itextpdf.kernel.font.PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD)
        val normalFont = com.itextpdf.kernel.font.PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA)
        val titleSize = 18f
        val normalSize = 12f

        val titleParagraph = Paragraph("Certificate of Completion")
            .setFont(titleFont)
            .setFontSize(24f)
            .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER)
        document.add(titleParagraph)

        document.add(Paragraph("Name:").setFont(titleFont).setFontSize(normalSize).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER))
        document.add(Paragraph(username ?: "Unknown Name").setFont(normalFont).setFontSize(normalSize).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER))

        document.add(Paragraph("Module Name:").setFont(titleFont).setFontSize(normalSize).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER))
        document.add(Paragraph(moduleName).setFont(titleFont).setFontSize(titleSize).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER))

        document.add(Paragraph("Score:").setFont(titleFont).setFontSize(normalSize).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER))
        document.add(Paragraph("$score/5").setFont(normalFont).setFontSize(normalSize).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER))

        document.close()
        writer.close()

        ToastManager.showToast("PDF saved", this, ToastType.SUCCESS)
        openPdf(file)
    }

    private fun openPdf(file: File) {
        val pdfUri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(pdfUri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        val chooser = Intent.createChooser(intent, "Open PDF")
        startActivity(chooser)
    }

}
