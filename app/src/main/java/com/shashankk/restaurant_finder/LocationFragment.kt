package com.shashankk.restaurant_finder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.shashankk.restaurant_finder.databinding.ContentMainBinding
import com.shashankk.vm.ILocationVM
import com.shashankk.vm.LocationVM

class LocationFragment : Fragment(), ILocationVM {

    private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var vm : LocationVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ContentMainBinding>(layoutInflater,R.layout.content_main, null, false)
        vm = LocationVM(this)
        binding.vm = vm
        return  binding.root
    }

    override fun startFetchingLocation() {
        context?.let {context ->
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions()
                return
            }

            with(context.getSystemService(Context.LOCATION_SERVICE) as LocationManager) {
                with(getLastKnownLocation(LocationManager.GPS_PROVIDER)) {
                    vm.onNewLocation(latitude, longitude)
                }
            }
        }
    }

    private fun requestPermissions() {
        activity?.let {
            ActivityCompat.requestPermissions(it, PERMISSIONS, 101)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (permissions.size != grantedPermissionCount(grantResults)) {
                Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show()
            } else {
                startFetchingLocation()
            }
        }
    }

    private fun grantedPermissionCount(grantResults: IntArray) = grantResults.filter { it!=-1 }.count()
}
