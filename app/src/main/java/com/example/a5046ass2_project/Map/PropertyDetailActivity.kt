package com.example.a5046ass2_project.Map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.a5046ass2_project.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PropertyDetailActivity : AppCompatActivity() {

    private lateinit var propertyDAO: PropertyDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        propertyDAO = PropertyDabase.getInstance(applicationContext).propertyDAO()

        // 获取传递的 ID 值
        val markerId = intent.getLongExtra("id", 0)

        // 使用 markerId 从数据库中获取相应的属性数据
        CoroutineScope(Dispatchers.IO).launch {
            val property = propertyDAO.getPropertyByMarkerId(markerId)

            withContext(Dispatchers.Main) {
                if (property != null) {
                    // 渲染属性详情
                    renderPropertyDetail(property)
                }
            }
        }
    }

    private fun renderPropertyDetail(property: Property) {
        // 在布局文件中定义的 TextView 控件
        val addressTextView: TextView = findViewById(R.id.addressTextView)
        val priceTextView: TextView = findViewById(R.id.priceTextView)
        val roomCountTextView: TextView = findViewById(R.id.roomCountTextView)
        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        val postcodeTextView: TextView = findViewById(R.id.postcodeTextView)

        // 使用属性数据填充 TextView 控件
        addressTextView.text = property.address
        priceTextView.text = property.price.toString()
        roomCountTextView.text = property.room_count.toString()
        descriptionTextView.text = property.description
        postcodeTextView.text = property.postcode.toString()
    }

}



