package com.example.hubbie.modules.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.hubbie.R
import com.example.hubbie.adapter.SectionsPagerAdapter
import com.example.hubbie.entities.Device
import com.example.hubbie.entities.Room
import com.example.hubbie.entities.User
import com.example.hubbie.entities.shared.AccountPreferences
import com.example.hubbie.modules.base.view.BaseFragment
import com.example.hubbie.modules.dialog.AddRoomFragmentDialog
import com.example.hubbie.modules.dialog.FragmentAddDeviceDialog
import com.example.hubbie.modules.main.IMain
import com.example.hubbie.modules.main.presenter.MainPresenter
import com.example.hubbie.utilis.GeneralUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainFragment : BaseFragment(), View.OnClickListener, ViewPager.OnPageChangeListener,
    DrawerLayout.DrawerListener, AddRoomFragmentDialog.EditRoomDialogCallbacks, IMain.View {

    private lateinit var mDrawerLayout: DrawerLayout

    private lateinit var mNavigationView: NavigationView

    private lateinit var mBottomNavigationView: BottomNavigationView
    private var pageSelected = 0
    private lateinit var mToolbar: Toolbar

    private lateinit var mActionBar: ActionBar

    private lateinit var tvTitle: TextView

    private lateinit var mViewPager: ViewPager

    private lateinit var llAdd: LinearLayout
    private lateinit var llStatistic: LinearLayout

    private lateinit var fabMenu: FloatingActionButton
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabStatistic: FloatingActionButton
    private lateinit var fabTvAdd: TextView

    private lateinit var prevMenu: MenuItem

    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

    private var presenter: MainPresenter? = null

    override fun onDrawerStateChanged(newState: Int) {
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerClosed(drawerView: View) {
    }

    override fun onDrawerOpened(drawerView: View) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    @SuppressLint("RestrictedApi")
    override fun onPageSelected(position: Int) {
        prevMenu.isChecked = false
        mBottomNavigationView.menu.getItem(position).isChecked = true
        tvTitle.text = mBottomNavigationView.menu.getItem(position).title
        prevMenu = mBottomNavigationView.menu.getItem(position)
        pageSelected = position
        when (position) {
            0 -> {
                fabMenu.visibility = View.VISIBLE
                fabTvAdd.text = "TẠO PHÒNG"
            }
            1 -> {
                fabMenu.visibility = View.INVISIBLE
            }
            2 -> {
                fabMenu.visibility = View.VISIBLE
                fabTvAdd.text = "THÊM THIẾT BỊ"
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_main, container, false)
        presenter = MainPresenter(this)
        val baseUser = AccountPreferences(context!!).getBaseAccount()

        Log.e("User", "User nameDisplay: ${baseUser.fullName} Role: ${baseUser.role}")


        LinearLayoutSetup(v)

        FABSetup(v) // Floating Action Bar setup

        ActionBarSetup(v)

        SubsSetup(v) //Toolbar - NavigationLayout - Titile setup

        NavigationViewSetup(v)

        BottomNavigationViewSetup(v)

        //Check role
        when (baseUser.role) {
            getString(R.string.admin) -> {
                mSectionsPagerAdapter =
                    SectionsPagerAdapter(fragmentManager!!, 3, this)
            }

            getString(R.string.room_admin) -> {
                mBottomNavigationView.menu.removeItem(R.id.bottom_nav_device)
                mSectionsPagerAdapter =
                    SectionsPagerAdapter(fragmentManager!!, 2, this)
            }

            getString(R.string.client) -> {
                mBottomNavigationView.menu.removeItem(R.id.bottom_nav_device)
                mBottomNavigationView.menu.removeItem(R.id.bottom_nav_user)
                mSectionsPagerAdapter =
                    SectionsPagerAdapter(fragmentManager!!, 1, this)
            }
        }
        StructureSetup(v) //ViewPager - Tablayout setup

        return v
    }

    //Component setup functions: This is first setup at onCreate function.
    private fun LinearLayoutSetup(view: View) {
        llAdd = view.findViewById(R.id.ll_add)
        llAdd.visibility = View.INVISIBLE
        llStatistic = view.findViewById(R.id.ll_statistic)
        llStatistic.visibility = View.INVISIBLE
    }

    private fun FABSetup(view: View) {
        fabMenu = view.findViewById(R.id.host_fab_menu)
        fabAdd = view.findViewById(R.id.fab_add)
        fabTvAdd = view.findViewById(R.id.fab_tv_add)
        fabTvAdd.text = "TẠO PHÒNG"
        fabStatistic = view.findViewById(R.id.fab_statistic)
        //fabAdd.visibility = View.INVISIBLE
        //fabStatistic.visibility =  View.INVISIBLE
        fabMenu.setOnClickListener(this)
        fabAdd.setOnClickListener(this)
        fabStatistic.setOnClickListener(this)
    }

    private fun ActionBarSetup(view: View) {
        mToolbar = view.findViewById(R.id.main_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        mActionBar = (activity as AppCompatActivity).supportActionBar!!
        mActionBar.setDisplayHomeAsUpEnabled(true)
        mActionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
    }

    private fun SubsSetup(view: View) {
        tvTitle = mToolbar.findViewById(
            R.id.main_toolbar_title
        )
        mDrawerLayout = view.findViewById(R.id.main_drawer_layout)
        mDrawerLayout.addDrawerListener(this)
    }

    private fun StructureSetup(view: View) {

        mViewPager = view.findViewById(R.id.vp_container)
        mViewPager.adapter = mSectionsPagerAdapter
        mViewPager.offscreenPageLimit = 3
        mViewPager.addOnPageChangeListener(this)
        //mTabLayout = findViewById(R.id.tab_layout)
        //mTabLayout.setupWithViewPager(mViewPager!!)
    }

    private fun NavigationViewSetup(view: View) {
        mNavigationView = view.findViewById(R.id.main_nav_view)
        mNavigationView.setNavigationItemSelectedListener { p0: MenuItem ->
            // set item as selected to persist highlight
            p0.isChecked = true
            when (p0.itemId) {

                R.id.nav_my_info -> {
                    presenter?.navigationUserInfo()
                }

                R.id.nav_chat -> {
                    presenter?.navigationListChat()
                }

                R.id.nav_app_info -> {

                }

            }
            //set ActionBar Title
            tvTitle.text = p0.title
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()
            //calling the method displayselectedscreen and passing the id of selected menu
            displaySelectedScreen(p0.itemId)
            true
        }
    }

    private fun BottomNavigationViewSetup(view: View) {
        mBottomNavigationView = view.findViewById(R.id.bottom_nav_view)
        tvTitle.text = "Rooms"
        prevMenu = mBottomNavigationView.menu.getItem(0)
        mBottomNavigationView.setOnNavigationItemSelectedListener { p0: MenuItem ->
            p0.isChecked = true
            tvTitle.text = p0.title
            displaySelectedScreen(p0.itemId)
            true
        }
    }

    override fun onRoomItemClicked(room: Room) {
        presenter?.onItemRoomClicked(room)
    }

    override fun onUserItemClicked(user: User) {
        presenter?.onItemUserClicked(user)
    }

    override fun onDeviceItemClicked(device: Device) {
        presenter?.onItemDeviceClicked(device)
    }


    private fun displaySelectedScreen(itemId: Int) {

        when (itemId) {
            R.id.nav_my_info -> {
            }
            R.id.bottom_nav_room -> {
                mViewPager.currentItem = 0
            }
            R.id.bottom_nav_user -> {
                mViewPager.currentItem = 1
            }
            R.id.bottom_nav_device -> {
                mViewPager.currentItem = 2
            }
            //R.id.bottom_nav_statistic -> { mViewPager.setCurrentItem(3) }
        }

    }

    override fun onDeleteRoom(room: Room) {

    }

    override fun onSaveRoom(room: Room) {
        presenter?.onNewRoomCreated(room)
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.host_fab_menu -> {
                GeneralUtils.showHideLayout(llAdd)
                GeneralUtils.showHideLayout(llStatistic)
            }
            R.id.fab_add -> {
                //Run custom dialog create unit
                when (pageSelected) {
                    //Room
                    0 -> {
                        fragmentManager?.beginTransaction()?.add(
                            AddRoomFragmentDialog.newInstance(this) as Fragment,
                            AddRoomFragmentDialog::class.java.simpleName
                        )?.commitAllowingStateLoss()
                    }
                    //Device
                    2 -> {
                        fragmentManager?.beginTransaction()?.add(
                            FragmentAddDeviceDialog(AccountPreferences(context!!).getBaseAccount().uid!!) as Fragment,
                            FragmentAddDeviceDialog::class.java.simpleName
                        )?.commitAllowingStateLoss()
                    }
                }

            }
            R.id.fab_statistic -> {
                //Run custom dialog statistic
            }
        }

    }

}