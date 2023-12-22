### User Flow :
1. User can login (if they have account) or User can register (if they don't have account).
2. User can see the top menu and top ingredients in the home page.
3. User can input the ingredients they have in find recipes page.
4. When user click on the "Find Recipes", the app will return recommended recipe.
5. User also can see the detail of the recipe.
6. In the detail page there will be ingredients, procedure of the recipe (The recipe can also be rating).
7. Lastly the profile page, provides the username and email of the user (User can also logout).

### Summary :
This project uses fragment for the home, find recipe, and profile page. We use fragment because we also 
implemented bottom navigation. We uses MVVM as its architecture pattern where the activity and fragment will observe data from the viewModel.
The viewModel will retrieve data from the API.

![Mockup Image](mockup.png)

### Tech Stack
* Kotlin Programming Language
* Retrofit2
* RecylerView
* Firebase Authentication
* Firebase Firestore
