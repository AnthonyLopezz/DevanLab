import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devan.lab.Activity.CourseDetailActivity
import com.devan.lab.Activity.ModuleDetailActivity
import com.devan.lab.Models.Module
import com.devan.lab.R

class ModuleAdapter : RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>() {

    private var modules: List<Module> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_module, parent, false)
        return ModuleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(modules[position])
        holder.itemView.setOnClickListener {
            val module = modules[position]
            holder.onClick(module.id)
        }
    }

    override fun getItemCount(): Int = modules.size

    fun setModules(newModules: List<Module>) {
        modules = newModules
        notifyDataSetChanged()
    }

    class ModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moduleTitle: TextView = itemView.findViewById(R.id.module_title)
        private val moduleDescription: TextView = itemView.findViewById(R.id.module_desc)

        fun bind(module: Module) {
            moduleTitle.text = module.title
            moduleDescription.text = module.description
        }

        fun onClick(id: Int) {
            Log.d("ModuleAdapter", "Module id $id")
            val context = itemView.context
            val intent = Intent(context, ModuleDetailActivity::class.java).apply {
                putExtra("module_id", id)
            }
            context.startActivity(intent)
        }
    }
}
