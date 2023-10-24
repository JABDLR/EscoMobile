package mx.ipn.escom.escomobile.app.vistas

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.crashlytics.android.Crashlytics
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import mx.ipn.escom.escomobile.app.R
import mx.ipn.escom.escomobile.app.config.AppConfig
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.modelo.firebase.MyFirebaseMessagingService


/**
 *   Clase vinculada con la interfaz de presentación: activity_main.xml
 *
 *   La clase tiene como función ser el contenedor donde se muestran la interface de presentación
 *    y los fragmentos fragmentRegistro y fragmentIniciarSesion
 */

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView

    /**
     * Aparte de la funcionalidad del onCreate(),
     *  se llaman a las funciones que declaran los captores de eventos mostrarMapa(), irARegistro() y btnIniciarSesion(),
     *  se habilita la visibilidad de los elementos img_escom (imagen), btn_registarse (botón), btn_iniciar_sesion (botón) y btn_coninuar_sin_registro (botón)
     *  y se genera la conexión con la base de datos en Firebase
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Firebase Analytics
        inicializarFirebase()

        // Inicializar el Singleton de las peticiones

        // Inicializar el Singleton de Room

        // Inicializar Crashlytics

        // Inicializar el Toolbar y el Navigation Drawer
        inicializarSideNav()
    }

    private fun inicializarSideNav() {
        val navController = findNavController(R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)

        // Se define que destinos son 'Top Level', en cuáles se debe mostrar el sideNav
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mapaFragment,
                R.id.inicioFragment,
                R.id.actividadesFragment,
                R.id.profesoresFragment,
                R.id.citasFragment,
                R.id.bolsaDeTrabajoFragment
            ),
            drawerLayout
        )

        // Le indica al Toolbar y al NavegationView que deben usar Navigation UI Component
        navView.setupWithNavController(navController)
        setSupportActionBar(toolbar)
        // Mostrar y admistrar el Drawer y el icono de regresar
        setupActionBarWithNavController(navController, drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // Se hace con nuestra implementacion de onNavigationItemSelected para extender
        // funcionalidad, y personalizar el Log, además de gestionar argumentos si son necesarios
        // navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    /**
     * Navegación a través del sideNav
     * Navega al destino con el id correspondiente al id del item del menu
     * Cierra la gabeta del menu, que por defecto queda abierta.
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        MyLogger.debug("Navegando a ${menuItem.title}")
        drawerLayout.closeDrawers()
        return NavigationUI.onNavDestinationSelected(menuItem, navController)
    }


    /**
     * Método que le indica al Toolbar cuando mostrar el BackButton y cuando no.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
        * Método que nos permite cachar cuando se da click en un item del menu del sideNav.
     */
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        MyLogger.debug("Navegando a ${item.itemId}.")
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    /**
     * Muestra o deshabilita el sideNav [navView] y el toolbar [toolbar]
     * Esto es para las vistas que no deben de mostrarlas, por ejemplo [BienvenidaFragment]
     * no muestra la navegación.
     * Ejemplo:
     * El comportamiento por defecto de una vista es mostrar el sideNav y toolbar el Fragment que
     * haga uso de este método debe hacerlo así:
     * onCreateView(...) {
     *    ...
     *   (activity as MainActivity).mostrarNavegacion(false)
     *    ...
     * }
     * y para no modificar el comportamiento de las demás vistas támbien debe y usar:
     * onStop(...) {
     *    ...
     *   (activity as MainActivity).mostrarNavegacion(true)
     *    ...
     * }
     */
    fun mostrarNavegacion(mostrar: Boolean) {
        if (mostrar) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toolbar.visibility = View.VISIBLE
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toolbar.visibility = View.GONE
        }
    }

    /**
     * Bloquea o desbloquea el cambio de orientación de la pantalla
     * Esto es para las vistas que no se aprecian de manera adecuada en modo Landscape.
     * Ejemplo:
     * El Fragment que haga uso de este método debe hacerlo así:
     * onResume(...) {
     *    ...
     *   (activity as MainActivity).bloquearOrientacion(true)
     *    ...
     * }
     * y para no modificar el comportamiento de las demás vistas támbien debe y usar:
     * onPause(...) {
     *    ...
     *   (activity as MainActivity).bloquearOrientacion(false)
     *    ...
     * }
     */
    fun bloquearOrientacion(bloquear: Boolean) {
        requestedOrientation = if (bloquear)
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else
            ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }


    /**
     * Método que nos permite instancias Firebase, además de suscribir al usuario a los canales por
     * defecto.
     */
    private fun inicializarFirebase() {
        // Inicializar la instancia de Firebase Analytics
        AppConfig.firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Inicializar Firebase Cloud Messages
        MyFirebaseMessagingService.getTokenfirebase { token: String ->
            Crashlytics.setUserIdentifier(token)
        }

        // Suscribir al usuario a los canales por defecto y bolsae de trabajo
        MyFirebaseMessagingService.subscribeToTopic(AppConfig.canalPorDefecto)
        MyFirebaseMessagingService.subscribeToTopic(AppConfig.canalBolsaTrabajo)

        MyFirebaseMessagingService.createNotificationChannel(this)

        // Obtener la instancia de Firebase Analytics
        AppConfig.firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}
