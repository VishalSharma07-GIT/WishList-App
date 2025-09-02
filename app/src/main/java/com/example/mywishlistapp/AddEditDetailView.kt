package com.example.mywishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import android.R.attr.id
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle


import androidx.compose.ui.tooling.preview.Preview
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.sp
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController

) {
    val snackMessage= remember{
        mutableStateOf("")
    }

    val scope= rememberCoroutineScope()
    val scaffoldState= rememberScaffoldState()
    if (id!=0L){
       val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }else{
        viewModel.wishTitleState=""
        viewModel.wishDescriptionState=""
    }
    Scaffold(

        topBar = {
            AppBarView(
                title =
                    if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish)
            ) {navController.navigateUp()}
            },
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        scaffoldState=scaffoldState,

      ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState,
                onValueChanged = { viewModel.onWishTitleChanged(it) }
            )

            Spacer(modifier = Modifier.width(250.dp))

            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChanged = { viewModel.onWishDescriptionChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (viewModel.wishTitleState.isNotEmpty() &&

                        viewModel.wishDescriptionState.isNotEmpty()) {
                        if(id!=0L){
                            viewModel.updateWish(
                                Wish(
                                    id=id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )

                            )
                            snackMessage.value="Updated!! "

                        }else{
                            // TODO ADD WISH
                            viewModel.addWish(
                                Wish(
                                    title=viewModel.wishTitleState.trim(),
                                    description=viewModel.wishDescriptionState.trim())
                            )
                            snackMessage.value="Wish has been created "
                        }

                    } else {
                        snackMessage.value="Enter fields to create a wish"
                    }
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }

                },
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF64B5F6),
                    contentColor = Color.White),

            shape = androidx.compose.foundation.shape.RoundedCornerShape(50))

            {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}


@Composable

fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(
        value=value,
        onValueChange = onValueChanged,
   label={Text(
       text = label,
       style= TextStyle(color= Color.Black))},
        modifier= Modifier.width(280.dp).height(56.dp),
        keyboardOptions= KeyboardOptions(keyboardType = KeyboardType.Text),
        colors= TextFieldDefaults.outlinedTextFieldColors(
            textColor=Color.Black,
            focusedBorderColor= colorResource(id=R.color.black),
            unfocusedBorderColor= colorResource(id=R.color.black),
            cursorColor= colorResource(id=R.color.black),
            focusedLabelColor= colorResource(id=R.color.black),
            unfocusedLabelColor= colorResource(id=R.color.black),
                )
        )



}
@Preview
@Composable
fun WishTestFieldPrev(){
    WishTextField(label="text", value="text", onValueChanged = {})
}

