package com.petsaude.usuario.gui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petsaude.R;
import com.petsaude.animal.gui.CadastroAnimal;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.clinica.gui.ClinicaDetalhe;
import com.petsaude.clinica.negocio.ClinicaService;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.negocio.UsuarioService;
import com.petsaude.vaga.negocio.VagaService;

public class MenuActivity extends android.support.v7.app.AppCompatActivity {

    public void onQuit(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);

        alertDialogBuilder.setTitle("Sair");
        alertDialogBuilder.setMessage("Deseja sair do aplicativo?");

        alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Session.setUsuarioLogado(null);
                Intent i = new Intent(MenuActivity.this, LoginAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public void onBackPressed(){
        onQuit();
    }


    // Declaring Your View and Variables
    Toolbar toolbar;

    private GoogleMap mMap;

    private MarkerOptions personMarker;

    final ClinicaService negocio = new ClinicaService(MenuActivity.this);
    final VagaService vagaNegocio = new VagaService(MenuActivity.this);



    String TITLES1[] = {"Consultas","Meu Perfil","Cadastrar Animal","Sair"};
    int ICONS[] = {R.drawable.ic_consulta,R.drawable.ic_profile,R.drawable.ic_cadastrar_pet,R.drawable.ic_action_sair};

    String NAME = Session.getUsuarioLogado().getLogin();
    String EMAIL = Session.getUsuarioLogado().getEmail();
    int PROFILE = R.drawable.ic_launcher;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setUpMapIfNeeded();

// ##################### Criacao do toolbar

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

//######################### Nav Drawer #############

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);
        // Letting the system know that the list objects are of fixed size

        mAdapter = new DrawerAdapter(TITLES1,ICONS,NAME,EMAIL,PROFILE,this);
        // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,menu_header view name, menu_header view email and context for adapter
        // and menu_header view profile picture

        mRecyclerView.setAdapter(mAdapter);
        // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);
        // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);
        // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        };

        // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State


        final GestureDetector mGestureDetector = new GestureDetector(MenuActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    int position = recyclerView.getChildAdapterPosition(child);
                    if (position == 1){
                        Intent i = new Intent(MenuActivity.this,ConsultaAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    if (position == 2){
                        Intent i = new Intent(MenuActivity.this,PerfilAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else if (position == 3 ){
                        Intent i = new Intent(MenuActivity.this,CadastroAnimal.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else if (position ==4){
                        onQuit();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

        });
//################## Fim nav Drawer #############
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        adicionaMarcadores();

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(-8.1929692,-34.9241879)).zoom(14).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        localizaUsuario();

        clicouNoMarcador();

    }

    public void adicionaMarcadores(){
        for (Clinica i : Session.getListaClinicas()){
            mMap.addMarker(new MarkerOptions().
                    position(new LatLng(Double.parseDouble(i.getLatitude()),Double.parseDouble(i.getLongitude()))).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.clinica_marker)).snippet(i.getId()+""));
        }
    }

    public void localizaUsuario(){
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(MenuActivity.this, "Infelizmente não conseguimos lhe localizar, arraste o marcador pra indicar sua localização atual.", Toast.LENGTH_LONG).show();
            personMarker.draggable(true);
        }
    }

    public void clicouNoMarcador(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override public boolean onMarkerClick(Marker marker) {
            int id = new Integer(marker.getSnippet());
                try {
                    Session.setClinicaSelecionada(negocio.getClinica(id));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("aqui","aqui");
                try {
                    Session.getClinicaSelecionada().setVagas(vagaNegocio.getVagas(Session.getClinicaSelecionada()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(MenuActivity.this, ClinicaDetalhe.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}