package uz.digital.volley103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import uz.digital.volley103.adapter.TodoAdapter
import uz.digital.volley103.databinding.ActivityMainBinding
import uz.digital.volley103.model.Todo
import uz.digital.volley103.util.Constants
import uz.digital.volley103.util.NetworkUtils
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val todoAdapter by lazy { TodoAdapter() }
    private val TAG = "MainActivity"
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        val networkUtils = NetworkUtils(this)
        if (networkUtils.isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(this)
            getAllTodos()
        } else {
            Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getAllTodos() {
        val jsonTodos = JsonArrayRequest(
            Request.Method.GET,
            Constants.BASE_URL,
            null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    binding.progressBar.isVisible = false
                    val type: Type = object : TypeToken<List<Todo>>() {}.type
                    val todoList = Gson().fromJson<List<Todo>>(response?.toString(), type)
                    todoAdapter.submitList(todoList)
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this@MainActivity, "${error?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
        requestQueue.add(jsonTodos)
    }
}