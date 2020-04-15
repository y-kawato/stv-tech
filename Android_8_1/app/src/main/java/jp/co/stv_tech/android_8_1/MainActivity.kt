package jp.co.stv_tech.android_8_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private var _menuList: MutableList<MutableMap<String, Any>>? = null
    private  val FROM = arrayOf("name",  "price")
    private val TO = intArrayOf(R.id.tvMenuName, R.id.tvMenuPrice)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("LifeCycleSample", "Main onCreate() called.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _menuList = createTeishokuList()

        val lvMenu = findViewById<ListView>(R.id.lvMenu)

        val menuList: MutableList<MutableMap<String, String>> = mutableListOf()


        val adapter = SimpleAdapter(applicationContext, _menuList, R.layout.row, FROM, TO)
        lvMenu.adapter = adapter

        lvMenu.onItemClickListener = ListItemClickListener()

        registerForContextMenu(lvMenu)
    }

    public override fun onStart() {
        Log.i("LifeCycleSample", "Main onStart() called.")
        super.onStart()
    }

    public override fun onRestart() {
        Log.i("LifeCycleSample", "Main onRestart() called.")
        super.onRestart()
    }

    public override fun onResume() {
        Log.i("LifeCycleSample", "Main onResume() called.")
        super.onResume()
    }

    public override fun onPause() {
        Log.i("LifeCycleSample", "Main onPause() called.")
        super.onPause()
    }

    public override fun onStop() {
        Log.i("LifeCycleSample", "Main onStop() called.")
        super.onStop()
    }

    public override fun onDestroy() {
        Log.i("LifeCycleSample", "Main onDestory() called.")
        super.onDestroy()
    }



    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            val item = parent.getItemAtPosition(position) as MutableMap<String, Any>

            order(item)
        }
    }

    private fun createTeishokuList():MutableList<MutableMap<String, Any>> {

        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()

        var menu = mutableMapOf("name" to "唐揚げ定食", "price" to 800,"desc" to "若鳥の唐揚げにサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)

        menu = mutableMapOf("name" to "ハンバーグ定食", "price" to 800,"desc" to "手ごねハンバーグにサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)

        return menuList

    }
    private fun createCurryList():MutableList<MutableMap<String, Any>> {

        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()

        var menu = mutableMapOf(
            "name" to "ビーフカレー",
            "price" to 520,
            "desc" to "特選スパイスをきかせた国産ビーフ100％のカレーです。"
        )
        menuList.add(menu)

        menu = mutableMapOf(
            "name" to "ポークカレー",
            "price" to 420,
            "desc" to "特選スパイスをきかせた国産ポーク100％のカレーです。"
        )
        menuList.add(menu)

        return menuList

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuListOptionTeishoku ->
                _menuList = createTeishokuList()
            R.id.menuListOptionCurry ->
                _menuList = createCurryList()
        }
        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        val adapter = SimpleAdapter(applicationContext, _menuList, R.layout.row, FROM, TO)
        lvMenu.adapter = adapter
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    )
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        menu?.setHeaderTitle(R.string.menu_list_context_header)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val listPosition = info.position
        val menu = _menuList!![listPosition]

        when(item.itemId) {
            R.id.menuListContextDesc -> {
                val desc = menu["desc"] as String
                Toast.makeText(applicationContext, desc,Toast.LENGTH_LONG).show()
            }
            R.id.menuListContextOrder ->

                order(menu)
        }
        return super.onContextItemSelected(item)
    }
    private fun order(menu: MutableMap<String, Any>) {

        val menuName = menu["name"] as String
        val menuPrice = menu["price"] as Int

        val intent = Intent(applicationContext, MenuThanksActivity::class.java)
        intent.putExtra("menuName", menuName)
        intent.putExtra("menuPrice", "${menuPrice}円")

        startActivity(intent)
    }
}
