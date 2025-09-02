package com.example.mywishlistapp


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywishlistapp.data.Wish
import com.example.mywishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch






class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
): ViewModel()
    {
        var wishTitleState by mutableStateOf("")
        var wishDescriptionState by mutableStateOf("")

        fun onWishTitleChanged(newString: String) {
            wishTitleState = newString
        }


        fun onWishDescriptionChanged(newString: String) {
            wishDescriptionState = newString
        }
        fun getAWishById(id:Long): Flow<Wish> {
            return wishRepository.getAWishById(id)
        }


        lateinit var getAllWishes: Flow<List<Wish>>


        init {
            viewModelScope.launch {
                getAllWishes= wishRepository.getWishes()
                //here it is saying that go to the repository and fetch all wishes and save them in my model
        }


    }
        fun addWish(wish:Wish){
            viewModelScope.launch(Dispatchers.IO) {
                wishRepository.addAWish(wish=wish)
            }
        }
        fun updateWish(wish:Wish){
            viewModelScope.launch(Dispatchers.IO) {
                wishRepository.updateWish(wish=wish)
            }
        }
        fun deleteWish(wish: Wish)
        {
            viewModelScope.launch (Dispatchers.IO){
                wishRepository.deleteAWISH(wish= wish)
            }
        }
}