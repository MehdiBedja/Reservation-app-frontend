/*
package com.example.reservation_app_frontend.screen.user

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.reservation_app_frontend.endpoint.user.userEndpoint
import com.example.reservation_app_frontend.repository.user.AuthRepository
import com.example.reservation_app_frontend.screen.navigation.Destination
import com.example.reservation_app_frontend.screen.parking.clearUsername
import com.example.reservation_app_frontend.viewModel.user.AccountViewModel
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel

@Composable
fun ShowProfile() {
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center){
        Button(onClick = {  }) {
            Text(text = "Profile")
        }

    }
}




@Composable
fun SignUpScreenOld(viewModel: AccountViewModel) {
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields for user registration data
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        // Sign-up button
        Button(
            onClick = {
                viewModel.signUpUser(
                    email = email.value,
                    username = username.value,
                    lastName = lastName.value,
                    firstName = firstName.value,
                    phoneNumber = phoneNumber.value,
                    password = password.value
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Sign Up")
        }

        // Show loading indicator and error message if applicable
        if (viewModel.loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        viewModel.error.value?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun SignUpScreen(navController: NavHostController) {

    val context = LocalContext.current


    val endpoint = userEndpoint.createEndpoint()
    val authRepository = AuthRepository(endpoint)
//    val viewModel = AccountViewModel.Factory(authRepository).create(AccountViewModel::class.java)
    val viewModel = AccountViewModel.getInstance(authRepository)

    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val createdSuccess by viewModel.createdSuccess

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields for user registration data
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        // Sign-up button with navigation action
        Button(
            onClick = {
                viewModel.signUpUser(
                    email = email.value,
                    username = username.value,
                    lastName = lastName.value,
                    firstName = firstName.value,
                    phoneNumber = phoneNumber.value,
                    password = password.value
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Sign Up")
        }

        // Show loading indicator and error message if applicable
        if (viewModel.loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        viewModel.error.value?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (viewModel.createdSuccess.value) {
            Log.d(ContentValues.TAG, "time to go to login page!!!!!!")
            LaunchedEffect(Unit) {

                navController.navigate(Destination.LogIn.route)

            }
        }
    }
}




@Composable
fun LogInScreen(navController: NavHostController , viewModel: LoginViewModel) {

    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    if(viewModel.login.value) {
        LaunchedEffect(Unit) {
            navController.navigate(Destination.ShowParkingList.route) {
                popUpTo(Destination.LogIn.route) {
                    inclusive = true
                }
            }
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayLoading(viewModel)
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {

                viewModel.loginUser(username.text , password.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        
        
        Button(
            onClick = {navController.navigate(Destination.SignUp.route) } ,
            modifier = Modifier.fillMaxWidth()
                
            
            
        )
        {
            Text(text = "Sign up ")
            
        }
    }
    
        
    //DisplayErrorMessage(viewModel)
    
        



}





@Composable
fun LogoutButton(navController: NavController, viewModel: LoginViewModel) {
        viewModel.logout()
        navController.navigate(Destination.LogIn.route)
}


@Composable
fun DisplayLoading(userModel: LoginViewModel) {
    val loading = userModel.loading.value
    if(loading) {
        CircularProgressIndicator()
    }

}


@Composable
fun DisplayErrorMessage(userModel: LoginViewModel) {
    val message = userModel.error.value
    if(!message?.isEmpty()!!) {
        Toast.makeText(LocalContext.current,message, Toast.LENGTH_SHORT).show()
    }
    userModel.error.value = ""

}
*/

package com.example.reservation_app_frontend.screen.user

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.reservation_app_frontend.R
import com.example.reservation_app_frontend.endpoint.user.userEndpoint
import com.example.reservation_app_frontend.repository.user.AuthRepository
import com.example.reservation_app_frontend.screen.navigation.Destination
import com.example.reservation_app_frontend.screen.parking.clearUsername
import com.example.reservation_app_frontend.viewModel.user.AccountViewModel
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel
import com.example.reservation_app_frontend.ui.theme.*

@Composable
fun ShowProfile() {
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center){
        Button(onClick = {  }) {
            Text(text = "Profile")
        }

    }
}



@Composable
fun SignUpScreen1(navController: NavHostController) {

    val context = LocalContext.current


    val endpoint = userEndpoint.createEndpoint()
    val authRepository = AuthRepository(endpoint)
//    val viewModel = AccountViewModel.Factory(authRepository).create(AccountViewModel::class.java)
    val viewModel = AccountViewModel.getInstance(authRepository)

    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val createdSuccess by viewModel.createdSuccess

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields for user registration data
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        // Sign-up button with navigation action
        Button(
            onClick = {
                viewModel.signUpUser(
                    email = email.value,
                    username = username.value,
                    lastName = lastName.value,
                    firstName = firstName.value,
                    phoneNumber = phoneNumber.value,
                    password = password.value
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Sign Up")
        }

        // Show loading indicator and error message if applicable
        if (viewModel.loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        viewModel.error.value?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (viewModel.createdSuccess.value) {
            Log.d(ContentValues.TAG, "time to go to login page!!!!!!")
            LaunchedEffect(Unit) {

                navController.navigate(Destination.LogIn.route)

            }
        }
    }
}







@Composable
fun SocialMediaLogIn(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .socialMedia()
            .clickable { onClick() }
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(text, style = MaterialTheme.typography.labelMedium)
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.socialMedia(): Modifier = composed {
    if (isSystemInDarkTheme()) {
        background(Color.Transparent).border(
            width = 1.dp,
            color = Color.Gray,
            shape = RoundedCornerShape(4.dp)
        )
    } else {
        background(Color.LightGray)
    }
}




@Composable
fun LogInScreen1(navController: NavHostController , viewModel: LoginViewModel) {

    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    if(viewModel.login.value) {
        LaunchedEffect(Unit) {
            navController.navigate(Destination.ShowParkingList.route) {
                popUpTo(Destination.LogIn.route) {
                    inclusive = true
                }
            }
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayLoading(viewModel)
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {

                viewModel.loginUser(username.text , password.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }


        Button(
            onClick = {navController.navigate(Destination.SignUp.route) } ,
            modifier = Modifier.fillMaxWidth()



        )
        {
            Text(text = "Sign up ")

        }
    }


    //DisplayErrorMessage(viewModel)





}





@Composable
fun LogInScreen(navController: NavHostController, viewModel: LoginViewModel) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    if (viewModel.login.value) {
        LaunchedEffect(Unit) {
            navController.navigate(Destination.ShowParkingList.route) {
                popUpTo(Destination.LogIn.route) {
                    inclusive = true
                }
            }
        }
    }

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSection()

            Spacer(modifier = Modifier.padding(14.dp))

            Column(modifier = Modifier.padding(horizontal = 30.dp)) {
                Down(username, password, onUsernameChange = { username = it }, onPasswordChange = { password = it }, onLoginClick = {
                    viewModel.loginUser(username.text, password.text)
                })

                Spacer(modifier = Modifier.height(30.dp))

//                GoogleFacebook()

                CreateAnAccount(navController)
            }
        }
    }
}

@Composable
private fun CreateAnAccount(navController: NavHostController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Box(
        modifier = Modifier
            .fillMaxHeight(fraction = 0.8f)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = Roboto
                    )
                ) {
                    append("Don't have an account?")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = Roboto
                    )
                ) {
                    append("  ")
                    append("Create One?")
                }
            },
            modifier = Modifier.clickable { navController.navigate(Destination.SignUp.route) }
        )
    }
}

@Composable
private fun GoogleFacebook() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "or continue with", style = MaterialTheme.typography.labelMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialMediaLogIn(
                icon = R.drawable.google,
                text = "Google",
                modifier = Modifier.weight(1f)
            ) {}
            Spacer(modifier = Modifier.width(20.dp))
            SocialMediaLogIn(
                icon = R.drawable.facebook,
                text = "Facebook",
                modifier = Modifier.weight(1f)
            ) {}
        }
    }
}

@Composable
private fun Down(username: TextFieldValue, password: TextFieldValue, onUsernameChange: (TextFieldValue) -> Unit, onPasswordChange: (TextFieldValue) -> Unit, onLoginClick: () -> Unit) {
    LoginTextField(label = "Username", value = username, onValueChange = onUsernameChange, trailing = "", modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.padding(15.dp))

    LoginTextField(label = "Password", value = password, onValueChange = onPasswordChange, trailing = "forgot?", modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.padding(15.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onLoginClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = "Log in",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TopSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Box(contentAlignment = Alignment.TopCenter) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.46f),
            painter = painterResource(id = R.drawable.shape),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier.padding(80.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "content description",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ParkiDZ",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
//                Text(
//                    text = "gggggggggggg",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = uiColor
//                )
            }
        }
        Text(
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.BottomCenter),
            text = "login",
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )
    }
}

@Composable
fun LoginTextField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, trailing: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        trailingIcon = {
            if (trailing.isNotEmpty()) {
                TextButton(onClick = { /* handle click */ }) {
                    Text(text = trailing)
                }
            }
        },
        visualTransformation = if (label == "Password") PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier
    )
}

//@SuppressLint("ModifierFactoryUnreferencedReceiver")
//fun Modifier.socialMedia(): Modifier = composed {
//    if (isSystemInDarkTheme()) {
//        background(Color.Transparent).border(
//            width = 1.dp,
//            color = Color.Gray,
//            shape = RoundedCornerShape(4.dp)
//        )
//    } else {
//        background(Color.LightGray)
//    }
//}






@Composable
fun LogoutButton(navController: NavController, viewModel: LoginViewModel) {
    viewModel.logout()
    navController.navigate(Destination.LogIn.route)
}


@Composable
fun DisplayLoading(userModel: LoginViewModel) {
    val loading = userModel.loading.value
    if(loading) {
        CircularProgressIndicator()
    }

}


@Composable
fun DisplayErrorMessage(userModel: LoginViewModel) {
    val message = userModel.error.value
    if(!message?.isEmpty()!!) {
        Toast.makeText(LocalContext.current,message, Toast.LENGTH_SHORT).show()
    }
    userModel.error.value = ""

}






















@Composable
fun SignUpScreen(navController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var username by remember { mutableStateOf(TextFieldValue()) }
    var lastName by remember { mutableStateOf(TextFieldValue()) }
    var firstName by remember { mutableStateOf(TextFieldValue()) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current


    val endpoint = userEndpoint.createEndpoint()
    val authRepository = AuthRepository(endpoint)
//    val viewModel = AccountViewModel.Factory(authRepository).create(AccountViewModel::class.java)
    val viewModel = AccountViewModel.getInstance(authRepository)

    if (viewModel.createdSuccess.value) {
        LaunchedEffect(Unit) {
            navController.navigate(Destination.LogIn.route)
        }
    }

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSectionSignUp()

            Spacer(modifier = Modifier.padding(14.dp))

            Column(modifier = Modifier.padding(horizontal = 30.dp)) {
                DownSignUp(
                    email, username, lastName, firstName, phoneNumber, password,
                    onEmailChange = { email = it },
                    onUsernameChange = { username = it },
                    onLastNameChange = { lastName = it },
                    onFirstNameChange = { firstName = it },
                    onPhoneNumberChange = { phoneNumber = it },
                    onPasswordChange = { password = it },
                    onSignUpClick = {
                        viewModel.signUpUser(
                            email.text, username.text, lastName.text, firstName.text, phoneNumber.text, password.text
                        )
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Optionally include GoogleFacebook() if needed
                AlreadyHaveAnAccount(navController)
            }
        }
    }
}

@Composable
private fun AlreadyHaveAnAccount(navController: NavHostController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Box(
        modifier = Modifier
            .fillMaxHeight(fraction = 0.8f)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = Roboto
                    )
                ) {
                    append("Already have an account?")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = Roboto
                    )
                ) {
                    append("  ")
                    append("Log in?")
                }
            },
            modifier = Modifier.clickable { navController.navigate(Destination.LogIn.route) }
        )
    }
}

@Composable
private fun DownSignUp(
    email: TextFieldValue,
    username: TextFieldValue,
    lastName: TextFieldValue,
    firstName: TextFieldValue,
    phoneNumber: TextFieldValue,
    password: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    onUsernameChange: (TextFieldValue) -> Unit,
    onLastNameChange: (TextFieldValue) -> Unit,
    onFirstNameChange: (TextFieldValue) -> Unit,
    onPhoneNumberChange: (TextFieldValue) -> Unit,
    onPasswordChange: (TextFieldValue) -> Unit,
    onSignUpClick: () -> Unit
) {
    SignUpTextField(label = "Email", value = email, onValueChange = onEmailChange)
    Spacer(modifier = Modifier.padding(8.dp))

    SignUpTextField(label = "Username", value = username, onValueChange = onUsernameChange)
    Spacer(modifier = Modifier.padding(8.dp))

    SignUpTextField(label = "Last Name", value = lastName, onValueChange = onLastNameChange)
    Spacer(modifier = Modifier.padding(8.dp))

    SignUpTextField(label = "First Name", value = firstName, onValueChange = onFirstNameChange)
    Spacer(modifier = Modifier.padding(8.dp))

    SignUpTextField(label = "Phone Number", value = phoneNumber, onValueChange = onPhoneNumberChange)
    Spacer(modifier = Modifier.padding(8.dp))

    SignUpTextField(label = "Password", value = password, onValueChange = onPasswordChange)
    Spacer(modifier = Modifier.padding(8.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onSignUpClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TopSectionSignUp() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Box(contentAlignment = Alignment.TopCenter) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.18f),
            painter = painterResource(id = R.drawable.shape),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier.padding(50.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "content description",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ParkiDZ",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
//                Text(
//                    text = "gggggggggggg",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = uiColor
//                )
            }
        }
        Text(
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.BottomCenter),
            text = "Sign Up",
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )
    }
}

@Composable
fun SignUpTextField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}
