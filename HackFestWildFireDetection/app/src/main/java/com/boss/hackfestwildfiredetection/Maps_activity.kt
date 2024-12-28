package com.boss.hackfestwildfiredetection

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.boss.hackfestwildfiredetection.databinding.ActivityMapsBinding
import org.json.JSONException
import org.json.JSONObject
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

class Maps_activity : AppCompatActivity() {
    lateinit var mMap: MapView
    lateinit var controller: IMapController
    lateinit var mMyLocationOverlay: MyLocationNewOverlay
    val url = "https://api.weatherapi.com/v1/forecast.json?key=2f138c0183174e969dc40454241901&q=dharan&days=1&aqi=yes&alerts=yes"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )




        // toast
        val latitudetest = intent.getDoubleExtra("latitude", 0.0)
        val longitudetest = intent.getDoubleExtra("longitude", 0.0)
        val radiustest = intent.getDoubleExtra("radius", 0.0)

        // Display the values (optional)

        //toast
//        Toast.makeText(
//            applicationContext,
//            "This city is " + longitudetest+
//                    " temp " + latitudetest+"radiustest"+radiustest,
//            Toast.LENGTH_LONG
//        ).show()





        // for api test
        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.get(url)
            // .addPathParameter()
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(jsonObject: JSONObject) {
                    Log.d("RES", jsonObject.toString())
                    try {
                        for (i in 0 until 10) {
                            val objResultCurrent = jsonObject.getJSONObject("current")
                            val objResultCondition = objResultCurrent.getJSONObject("condition")
                            val objResultQuality = objResultCurrent.getJSONObject("air_quality")
                            val objResultQuality1 = objResultCurrent.getJSONObject("condition")
                            val objResultLocation = jsonObject.getJSONObject("location")

//                            binding.place.text = objResultLocation.getString("name")
//                            binding.temperature.text = objResultCurrent.getDouble("temp_c").toString()
//                            binding.carbon.text = objResultQuality.getDouble("co").toString()
//                            binding.humidity.text = objResultCurrent.getDouble("humidity").toString()
//                            binding.weather.text = objResultCondition.getString("text")

                            // for ml integration
                            AndroidNetworking.post(" http://127.0.0.1:8000/predict")
                                .addBodyParameter("Temperature", objResultCurrent.getDouble("temp_c").toString())
                                .addBodyParameter("RelativeHumidity", objResultCurrent.getDouble("humidity").toString())
                                .addBodyParameter("WindSpeed", "0")
                                .addBodyParameter("FFMC", "0")
                                .addBodyParameter("Oxygen", "0")
                                .addBodyParameter("TopographyFlat", "0")
                                .addBodyParameter("TopographyHill", "0")
                                .addBodyParameter("TopographyMountain", "0")
                                .addBodyParameter("LightingActivityModerate", "0")
                                .addBodyParameter("LightingActivityNone", "0")
                                .addBodyParameter("LightingActivitySevere", "0")
                                .addBodyParameter("HumanActivityHigh", "0")
                                .addBodyParameter("HumanActivityLow", "0")
                                .addBodyParameter("HumanActivityMedium", "0")
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(object : JSONObjectRequestListener {
                                    override fun onResponse(response: JSONObject) {
                                        try {
                                            val prediction = response.getString("prediction")
                                            Toast.makeText(this@Maps_activity, "Prediction: $prediction", Toast.LENGTH_SHORT).show()
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }

                                    override fun onError(anError: ANError) {
//                                        Toast.makeText(this@Maps_activity, "Error: ${anError.message}", Toast.LENGTH_SHORT).show()
                                        Log.d("this is first error",anError.toString())
                                    }
                                })
                            // for ml integration



//
//                            Toast.makeText(
//                                applicationContext,
//                                "This city is " + objResultLocation.getString("name") +
//                                        " temp " + objResultCurrent.getDouble("temp_c").toString(),
//                                Toast.LENGTH_SHORT
//                            ).show()

                        }
                    } catch (e: JSONException) {
//                        throw RuntimeException(e)
                        Log.d("error in networking",""+e);
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.e("ERROR", anError?.message.toString())
                }
            })
        // for api test
        mMap = binding.osmmap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.setMultiTouchControls(true)

        controller = mMap.controller

        val latitude = 28.242848
        val longitude = 84.009416
        val latitude1 = 28.243949
        val longitude1 = 84.009716
        val latitude2 = 28.243049
        val longitude2 = 84.008016

        // Call the reusable function to draw the circle and text
        drawCircleWithTextMedium(mMap, latitude, longitude, radiusKm = 0.040, "62%")
        drawCircleWithTextHigh(mMap, latitude1, longitude1, radiusKm = 0.030, "80%")
        drawCircleWithText(mMap, latitude2, longitude2, radiusKm = 0.010, "32%")


        drawCircleWithTextSelector(mMap, latitude, longitude, radiusKm = .510, "80%")


        controller.setZoom(18.7)  // Set zoom level
        controller.setCenter(GeoPoint(latitude, longitude))


        // for smaller circle
        val centerLatitude = 28.242848
        val centerLongitude = 84.009416
        val largerRadiusKm = .510 // Radius of the larger circle in kilometers
        val numberOfSmallerCircles = 32

        val smallerCircleCenters = calculateSmallerCirclesInLargerCircle(
            centerLatitude,
            centerLongitude,
            largerRadiusKm,
            numberOfSmallerCircles
        )

// Print the centers of the smaller circles
        smallerCircleCenters.forEachIndexed { index, geoPoint ->
//            drawCircleWithText(mMap, geoPoint.latitude, geoPoint.longitude, radiusKm = sqrt(.051*.51/32), "50%")
        }
        // for smaller circle

    }

    private fun drawCircleWithTextHigh(map: MapView, latitude: Double, longitude: Double, radiusKm: Double, text: String) {
        // Convert radius from kilometers to degrees
        val radiusInDegrees = radiusKm / 111.0 // 1 degree ≈ 111 km

        // Create the center GeoPoint
        val centerPoint = GeoPoint(latitude, longitude)

        // Function to calculate a point on the circle at a given angle
        fun getCirclePoint(center: GeoPoint, angle: Double, radiusInDegrees: Double): GeoPoint {
            val lat = center.latitude + radiusInDegrees * cos(angle)
            val lon = center.longitude + radiusInDegrees * sin(angle) / cos(center.latitude * PI / 180.0)
            return GeoPoint(lat, lon)
        }

        // Generate points around the circle
        val circlePoints = mutableListOf<GeoPoint>()
        val numberOfPoints = 360 // Number of points to form the circle
        for (i in 0 until numberOfPoints) {
            val angle = i * (2 * PI / numberOfPoints)
            circlePoints.add(getCirclePoint(centerPoint, angle, radiusInDegrees))
        }

        // Create the polygon for the circle
        val circlePolygon = Polygon().apply {
            points = circlePoints
            fillColor = Color.argb(165, 255, 0, 0) // Transparent red
            strokeColor = Color.BLACK // Solid red outline
            strokeWidth = 2f
        }


        // Add the circle polygon to the map overlays
        map.overlays.add(circlePolygon)

        // Add text overlay
        val textOverlay = object : Overlay() {
            override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
                if (shadow) return // Skip drawing shadows

                // Convert GeoPoint to screen coordinates
                val screenPoint = Point()
                mapView.projection.toPixels(centerPoint, screenPoint)

                // Calculate the scaled text size
                val zoomLevel = mapView.zoomLevelDouble
                val baseTextSize = 60f // Base text size
                val scale = 1 / zoomLevel // Scale inversely with zoom level
                val adjustedTextSize = baseTextSize * scale.coerceAtLeast(0.5) // Prevent text size from being too small

                // Set up the paint for the text
                val textPaint = Paint().apply {
                    color = Color.BLACK
                    textSize = adjustedTextSize.toFloat()
                    isAntiAlias = true
                    textAlign = Paint.Align.CENTER
                }

                // Draw the text on the canvas
                canvas.drawText(text, screenPoint.x.toFloat(), screenPoint.y.toFloat(), textPaint)
            }
        }

        // Add the custom overlay to the map
        map.overlays.add(textOverlay)
    }
    private fun drawCircleWithTextMedium(map: MapView, latitude: Double, longitude: Double, radiusKm: Double, text: String) {
        // Convert radius from kilometers to degrees
        val radiusInDegrees = radiusKm / 111.0 // 1 degree ≈ 111 km

        // Create the center GeoPoint
        val centerPoint = GeoPoint(latitude, longitude)

        // Function to calculate a point on the circle at a given angle
        fun getCirclePoint(center: GeoPoint, angle: Double, radiusInDegrees: Double): GeoPoint {
            val lat = center.latitude + radiusInDegrees * cos(angle)
            val lon = center.longitude + radiusInDegrees * sin(angle) / cos(center.latitude * PI / 180.0)
            return GeoPoint(lat, lon)
        }

        // Generate points around the circle
        val circlePoints = mutableListOf<GeoPoint>()
        val numberOfPoints = 360 // Number of points to form the circle
        for (i in 0 until numberOfPoints) {
            val angle = i * (2 * PI / numberOfPoints)
            circlePoints.add(getCirclePoint(centerPoint, angle, radiusInDegrees))
        }

        // Create the polygon for the circle
        val circlePolygon = Polygon().apply {
            points = circlePoints
            fillColor = Color.argb(100, 255, 0, 0) // Transparent red
            strokeColor = Color.RED // Solid red outline
            strokeWidth = 2f
        }


        // Add the circle polygon to the map overlays
        map.overlays.add(circlePolygon)

        // Add text overlay
        val textOverlay = object : Overlay() {
            override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
                if (shadow) return // Skip drawing shadows

                // Convert GeoPoint to screen coordinates
                val screenPoint = Point()
                mapView.projection.toPixels(centerPoint, screenPoint)

                // Calculate the scaled text size
                val zoomLevel = mapView.zoomLevelDouble
                val baseTextSize = 60f // Base text size
                val scale = 1 / zoomLevel // Scale inversely with zoom level
                val adjustedTextSize = baseTextSize * scale.coerceAtLeast(0.5) // Prevent text size from being too small

                // Set up the paint for the text
                val textPaint = Paint().apply {
                    color = Color.BLACK
                    textSize = adjustedTextSize.toFloat()
                    isAntiAlias = true
                    textAlign = Paint.Align.CENTER
                }

                // Draw the text on the canvas
                canvas.drawText(text, screenPoint.x.toFloat(), screenPoint.y.toFloat(), textPaint)
            }
        }

        // Add the custom overlay to the map
        map.overlays.add(textOverlay)
    }
    private fun drawCircleWithText(map: MapView, latitude: Double, longitude: Double, radiusKm: Double, text: String) {
        // Convert radius from kilometers to degrees
        val radiusInDegrees = radiusKm / 111.0 // 1 degree ≈ 111 km

        // Create the center GeoPoint
        val centerPoint = GeoPoint(latitude, longitude)

        // Function to calculate a point on the circle at a given angle
        fun getCirclePoint(center: GeoPoint, angle: Double, radiusInDegrees: Double): GeoPoint {
            val lat = center.latitude + radiusInDegrees * cos(angle)
            val lon = center.longitude + radiusInDegrees * sin(angle) / cos(center.latitude * PI / 180.0)
            return GeoPoint(lat, lon)
        }

        // Generate points around the circle
        val circlePoints = mutableListOf<GeoPoint>()
        val numberOfPoints = 360 // Number of points to form the circle
        for (i in 0 until numberOfPoints) {
            val angle = i * (2 * PI / numberOfPoints)
            circlePoints.add(getCirclePoint(centerPoint, angle, radiusInDegrees))
        }

        // Create the polygon for the circle
        val circlePolygon = Polygon().apply {
            points = circlePoints
            fillColor = Color.argb(50, 255, 255, 0,) // Transparent red
            strokeColor = Color.YELLOW // Solid red outline
            strokeWidth = 2f
        }


        // Add the circle polygon to the map overlays
        map.overlays.add(circlePolygon)

        // Add text overlay
        val textOverlay = object : Overlay() {
            override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
                if (shadow) return // Skip drawing shadows

                // Convert GeoPoint to screen coordinates
                val screenPoint = Point()
                mapView.projection.toPixels(centerPoint, screenPoint)

                // Calculate the scaled text size
                val zoomLevel = mapView.zoomLevelDouble
                val baseTextSize = 60f // Base text size
                val scale = 1 / zoomLevel // Scale inversely with zoom level
                val adjustedTextSize = baseTextSize * scale.coerceAtLeast(0.5) // Prevent text size from being too small

                // Set up the paint for the text
                val textPaint = Paint().apply {
                    color = Color.BLACK
                    textSize = adjustedTextSize.toFloat()
                    isAntiAlias = true
                    textAlign = Paint.Align.CENTER
                }

                // Draw the text on the canvas
                canvas.drawText(text, screenPoint.x.toFloat(), screenPoint.y.toFloat(), textPaint)
            }
        }

        // Add the custom overlay to the map
        map.overlays.add(textOverlay)
    }
    private fun drawCircleWithTextSelector(map: MapView, latitude: Double, longitude: Double, radiusKm: Double, text: String) {
        // Convert radius from kilometers to degrees
        val radiusInDegrees = radiusKm / 111.0 // 1 degree ≈ 111 km

        // Create the center GeoPoint
        val centerPoint = GeoPoint(latitude, longitude)

        // Function to calculate a point on the circle at a given angle
        fun getCirclePoint(center: GeoPoint, angle: Double, radiusInDegrees: Double): GeoPoint {
            val lat = center.latitude + radiusInDegrees * cos(angle)
            val lon = center.longitude + radiusInDegrees * sin(angle) / cos(center.latitude * PI / 180.0)
            return GeoPoint(lat, lon)
        }

        // Generate points around the circle
        val circlePoints = mutableListOf<GeoPoint>()
        val numberOfPoints = 360 // Number of points to form the circle
        for (i in 0 until numberOfPoints) {
            val angle = i * (2 * PI / numberOfPoints)
            circlePoints.add(getCirclePoint(centerPoint, angle, radiusInDegrees))
        }

        // Create the polygon for the circle
        val circlePolygon = Polygon().apply {
            points = circlePoints
            fillColor = Color.argb(18, 255, 0, 0) // Transparent red
            strokeColor = Color.YELLOW // Solid red outline
            strokeWidth = 2f
        }

        // Add the circle polygon to the map overlays
        map.overlays.add(circlePolygon)

//        // Add text overlay
//        val textOverlay = object : Overlay() {
//            override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
//                if (shadow) return // Skip drawing shadows
//
//                // Convert GeoPoint to screen coordinates
//                val screenPoint = Point()
//                mapView.projection.toPixels(centerPoint, screenPoint)
//
//                // Calculate the scaled text size
//                val zoomLevel = mapView.zoomLevelDouble
//                val baseTextSize = 60f // Base text size
//                val scale = 1 / zoomLevel // Scale inversely with zoom level
//                val adjustedTextSize = baseTextSize * scale.coerceAtLeast(0.5) // Prevent text size from being too small
//
//                // Set up the paint for the text
//                val textPaint = Paint().apply {
//                    color = Color.BLACK
//                    textSize = adjustedTextSize.toFloat()
//                    isAntiAlias = true
//                    textAlign = Paint.Align.CENTER
//                }
//
//                // Draw the text on the canvas
//                canvas.drawText(text, screenPoint.x.toFloat(), screenPoint.y.toFloat(), textPaint)
//            }
//        }

        // Add the custom overlay to the map
//        map.overlays.add(textOverlay)
    }
    fun calculateSmallerCirclesInLargerCircle(
        centerLatitude: Double,
        centerLongitude: Double,
        largerRadiusKm: Double,
        numberOfSmallerCircles: Int
    ): List<GeoPoint> {
        val smallerCircleCenters = mutableListOf<GeoPoint>()

        // Convert the larger circle radius from kilometers to degrees
        val largerRadiusInDegrees = largerRadiusKm / 111.0

        // Number of layers (rings) within the larger circle
        val numLayers = ceil(sqrt(numberOfSmallerCircles.toDouble())).toInt()

        // Loop through each layer to calculate the points
        var remainingCircles = numberOfSmallerCircles
        for (layer in 1..numLayers) {
            // Radius of the current layer
            val layerRadius = (layer / numLayers.toDouble()) * largerRadiusInDegrees

            // Number of circles in this layer (proportional to circumference)
            val circlesInLayer = min(remainingCircles, 6 * layer)

            // Angle step for evenly spacing the circles in the layer
            val angleStep = 2 * PI / circlesInLayer

            // Calculate positions of the circles in the current layer
            for (i in 0 until circlesInLayer) {
                val angle = i * angleStep
                val lat = centerLatitude + layerRadius * cos(angle)
                val lon = centerLongitude + layerRadius * sin(angle) / cos(centerLatitude * PI / 180.0)
                smallerCircleCenters.add(GeoPoint(lat, lon))
            }

            // Update remaining circles
            remainingCircles -= circlesInLayer
            if (remainingCircles <= 0) break
        }

        return smallerCircleCenters
    }


//
//    override fun onScroll(event: ScrollEvent?): Boolean {
//        Log.e("TAG", "onScroll Latitude: ${event?.source?.mapCenter?.latitude}")
//        Log.e("TAG", "onScroll Longitude: ${event?.source?.mapCenter?.longitude}")
//        return true
//    }
//
//    override fun onZoom(event: ZoomEvent?): Boolean {
//        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}")
//        return false
//    }
//
//    override fun onGpsStatusChanged(event: Int) {
//        TODO("Not yet implemented")
//    }
}