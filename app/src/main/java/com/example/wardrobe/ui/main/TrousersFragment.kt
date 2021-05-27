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
class TrousersFragment : Fragment() {

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
        // private const val ARG_FRAGMENT_SHUFFLE = "is_shuffle"

        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(position: Int/*, isShuffle: Boolean*/): TrousersFragment {
            return TrousersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FRAGMENT_POSITION, position)
                    // putBoolean(ARG_FRAGMENT_SHUFFLE, isShuffle)
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
        val imageViewTrousers: ImageView = root.findViewById(R.id.imageView)

        val fragmentPosition = arguments?.getInt(ARG_FRAGMENT_POSITION) ?: 0

        /*pageViewModel.loadTrouserImageList().observe(viewLifecycleOwner, Observer<List<Trouser>> {
            for (image in it){
                Log.e("TEST", "trouser_frag_image is $image")
            }

            if(context != null){
                val file = File.createTempFile("image", null, context?.cacheDir)

                pageViewModel.getBase64ImageFromList(it[fragmentPosition].image, file)
                Picasso.with(requireActivity())
                    .load(file)
                    // .placeholder(R.drawable.ic_launcher_background)
                    .into(imageViewTrousers)
            }
        })*/

        if(context != null){
            val file = File.createTempFile("image", null, context?.cacheDir)

            pageViewModel.getBase64ImageFromList(pageViewModel.getTrouserList()[fragmentPosition].image, file)
            Picasso.with(requireActivity())
                .load(file)
                // .placeholder(R.drawable.ic_launcher_background)
                .into(imageViewTrousers)
        }

        return root
    }

}