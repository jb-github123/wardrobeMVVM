package com.example.wardrobe.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wardrobe.R
import com.squareup.picasso.Picasso
import java.io.*

/**
 * A fragment for swiping shirts
 */
class ShirtsFragment : Fragment() {

    /*interface AddShirtImageListener {
        fun addShirt()
    }*/

    // private val pageViewModel: PageViewModel by activityViewModels()
    private lateinit var pageViewModel: PageViewModel

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_FRAGMENT_POSITION = "fragment_position"
        // private const val ARG_FRAGMENT_LIST = "fragment_list"

        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(position: Int/*, list: List<Any>*/): ShirtsFragment {
            return ShirtsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FRAGMENT_POSITION, position)
                    // putSerializable(ARG_FRAGMENT_LIST, list)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(activity!!).get(PageViewModel::class.java)/*.apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 0)
        }*/
        // pageViewModel?.let { lifecycle.addObserver(it) }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val imageViewShirts: ImageView = root.findViewById(R.id.imageView)

        /*Picasso.with(requireActivity())
            .load(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imageViewShirts)*/

        // val isShuffle = arguments?.getBoolean(ARG_FRAGMENT_SHUFFLE) ?: false
        val fragmentPosition = arguments?.getInt(ARG_FRAGMENT_POSITION) ?: 0

        /* pageViewModel.loadShirtImageList().observe(viewLifecycleOwner, Observer<List<Shirt>> {
            for ((index, image) in it.withIndex()){
                Log.e("TEST", "shirt_frag_image is $index ->  $image")
            }

            if(context != null){
                val file = File.createTempFile("image", null, context?.cacheDir)

                pageViewModel.getBase64ImageFromList(it[fragmentPosition].image, file)
                Picasso.with(requireActivity())
                    .load(file)
                    // .placeholder(R.drawable.ic_launcher_background)
                    .into(imageViewShirts)
            }
        }) */

        if(context != null){
            val file = File.createTempFile("image", null, context?.cacheDir)

            pageViewModel.getBase64ImageFromList(pageViewModel.getShirtList()[fragmentPosition].image, file)
            Picasso.with(requireActivity())
                .load(file)
                // .placeholder(R.drawable.ic_launcher_background)
                .into(imageViewShirts)
        }

        /*if(isShuffle){
            pageViewModel.shuffleList().observe(viewLifecycleOwner, Observer<List<Shirt>> {
                for (image in it){
                    Log.e("TEST", "shuffle image is $image")
                }

                if(context != null){
                    val file = File.createTempFile("image", null, context?.cacheDir)

                    pageViewModel.getShirtImageFromList(it[fragmentPosition], file)
                    Picasso.with(requireActivity())
                        .load(file)
                        // .placeholder(R.drawable.ic_launcher_background)
                        .into(imageViewShirts)
                }
            })
        } else {

        }*/

        // pageViewModel.imageListArray.removeObserver(this)

        // load image list from DB
        // pageViewModel.loadShirtImageList()
        // pageViewModel.shuffleList()

        return root
    }



}