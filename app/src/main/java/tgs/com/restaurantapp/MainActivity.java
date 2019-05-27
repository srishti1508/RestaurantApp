package tgs.com.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.daimajia.slider.library.SliderLayout;
import java.util.ArrayList;
import java.util.List;
import static tgs.com.restaurantapp.Login.PREFS_NAME;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ActionBarDrawerToggle drawerToggle;
    CoordinatorLayout rootLayout;
    Toolbar toolbar;
    TextView marquee, notice;
    private SliderLayout mDemoSlider;
    public static String user_name;
    public static String user_id;
    public static String type;
    public static String user_main_id;
    public static String stu_cource_id;
    public static String stu_branch_id;
    public static String stu_sem_id;
    public static String stu_section;
    public static String stu_session_year;
    public static String user_logged_type;
    private List<Album> albumList;
    private AlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupDeawer();
        albumList = new ArrayList<>();
        adapter = new AlbumAdapter(this, albumList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        user_name = settings.getString("user_name", "");
        type = settings.getString("user_type", "");
        user_id = settings.getString("user_id", "");
        user_main_id = settings.getString("base_id", "");
        stu_cource_id = settings.getString("course_id", "");
        stu_branch_id = settings.getString("branch_id", "");
        stu_sem_id = settings.getString("student_semester_id", "");
        stu_section = settings.getString("student_section", "");
        stu_session_year = settings.getString("session_year", "");
        user_logged_type = settings.getString("user_logged_type", "");


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        prepareAlbums();
        /*if(settings.getString("user_type", "").toString().equals("admin")){

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
            prepareAlbums();
        }*/
    }
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.expense_report,
                R.drawable.report,
                R.drawable.rest_customer,
                R.drawable.tablechair,
                R.drawable.product_report,
                R.drawable.menu,
                R.drawable.restaurant,
        };
        Album a = new Album("Expense Report", 13, covers[0]);
        albumList.add(a);
        a = new Album("Sales Report", 11, covers[1]);
        albumList.add(a);
        a= new Album("Customers", 11, covers[2]);
        albumList.add(a);
        a = new Album("Tables", 12, covers[3]);
        albumList.add(a);
        a= new Album("Product Report", 14, covers[4]);
        albumList.add(a);
       /* a = new Album("Events.", 14, covers[8]);
        albumList.add(a);*/
        adapter.notifyDataSetChanged();
    }
    private void setupDeawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, toolbar, R.string.action_play_next, R.string.action_settings);
        mDrawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        //  rootLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_drawer);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.clear().commit();
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}