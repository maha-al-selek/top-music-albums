
# Top Music Albums App

This project is an Android application that displays the top 100 music albums in the US. It uses Jetpack Compose for the UI, follows the MVVM architecture, and incorporates dependency injection using Hilt. The data is fetched from a remote API and stored locally using Realm database.

## Features

- **Top Albums Display**: The app displays the top 100 music albums in a grid view on the home screen.
- **Album Details**: Tapping on an album displays detailed information about the album, including the name, artist, genres, release date, and a link to the album in the iTunes store.
- **Offline Access**: The app stores album data locally to allow access without a network connection after the first load.
- **Data Refresh**: The app refreshes the data with a network call if a connection is available. Users can also manually refresh the data.

## Architecture

- **MVVM (Model-View-ViewModel)**: The app follows the MVVM architecture to separate concerns and improve testability and maintainability.
- **DI (Dependency Injection)**: Hilt is used for dependency injection to manage the application's dependencies.
- **Jetpack Compose**: The UI is built using Jetpack Compose for a modern and declarative approach to UI development.

## Libraries Used

- **Jetpack Compose**: For building the UI.
- **Hilt**: For dependency injection.
- **Retrofit**: For network calls.
- **Realm**: For local data storage.
- **Gson**: For JSON parsing.

## Requirements

- Android Studio Flamingo or later.
- Gradle 7.0.0 or later.

## Setup Instructions

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/your-repo/top-music-albums.git
    cd top-music-albums
    ```

2. **Open in Android Studio**: Open the project in Android Studio.

3. **Build the Project**: Build the project to download all dependencies.

4. **Run the App**: Run the app on an emulator or a physical device.

## Project Structure

- **MainActivity**: The entry point of the application.
- **NavGraph**: Defines the navigation graph for the app.
- **viewmodel/**: Contains the ViewModels for the app.
- **repository/**: Contains the DataRepository for data fetching and storage.
- **network/**: Contains the Retrofit service and API interface.
- **di/**: Contains the Hilt modules for dependency injection.
- **ui/**: Contains the composables for the UI.
  - **components/**: Contains reusable UI components.
  - **homescreen/**: Contains the HomeScreen composable and its related components.
  - **albumdetailscreen/**: Contains the AlbumDetailScreen composable and its related components.
- **model/**: Contains the data models for the API and database.
  - **api/**: Contains the data models for the API responses.
  - **db/**: Contains the Realm data models.

## Code and Design Decisions

### Code Organization

The project follows a modular approach with separate packages for different layers (network, repository, viewmodel, ui).
Dependency Injection (DI) is managed using Hilt, which simplifies the process of providing and injecting dependencies across the application.

### Data Fetching and Storage

Data is fetched from a remote API using Retrofit and parsed using Gson.
The fetched data is stored locally using Realm, allowing offline access to the data.
The app checks for network availability before making API calls. If the network is not available, it fetches the data from the local Realm database.

### UI Design

The UI is built using Jetpack Compose, which provides a modern and declarative approach to building UIs on Android.
The home screen displays the top albums in a grid view, using a `LazyVerticalGrid` composable.
The detail screen provides detailed information about the selected album, including a larger image, genres displayed as rounded tags with borders, release date, copyright information, and a button to visit the album page on iTunes.
The button color and other UI elements are customized to match the design requirements.

### Error Handling

The app includes error handling for network failures and data fetching errors.
Users are informed of errors through appropriate UI messages.
The app allows users to retry network calls if the initial attempt fails and no data is available in the local database.

## Important UI Code Snippets

### HomeScreen Composable

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current
    viewModel.fetchAlbums(context)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val dataState = uiState.value) {
            is State.Loading -> {
                CircularProgressIndicator()
            }
            is State.Success -> dataState.data?.let { AlbumsGrid(albums = it, navController = navController) }
            is State.Error -> Text(text = dataState.message, fontSize = 20.sp)
        }
    }
}
```

### AlbumsGrid Composable

```kotlin
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumsGrid(albums: List<RealmAlbum>, navController: NavController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(albums) { album ->
            AlbumCard(album = album, navController = navController)
        }
    }
}
```

### AlbumCard Composable

```kotlin
@Composable
fun AlbumCard(album: RealmAlbum, navController: NavController) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("albumDetail/${album.id}") }
    ) {
        Column {
            AsyncImage(
                model = album.albumThumbnail,
                contentDescription = "Album Image",
                modifier = Modifier.size(128.dp)
            )
            Text(
                text = album.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            Text(
                text = album.artistName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontSize = 12.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}
```

### AlbumDetailScreen Composable

```kotlin
@Composable
fun AlbumDetailScreen(albumId: Int?, viewModel: AlbumDetailViewModel = hiltViewModel(), navController: NavController) {
    if (albumId == null) {
        Text("Album not found")
        return
    }

    LaunchedEffect(albumId) {
        viewModel.fetchAlbumById(albumId)
    }

    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    when (val state = uiState.value) {
        is State.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is State.Success -> {
            val album = state.data!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Box {
                    AlbumImage(album.albumThumbnail)
                    BackButton(onBackPressed = { navController.navigateUp() })
                }
                Spacer(modifier = Modifier.height(16.dp))
                AlbumInfo(
                    name = album.name,
                    artistName = album.artistName,
                    genres = album.genre.map { it.name }
                )
                Spacer(modifier = Modifier.weight(1f))
                AlbumDetailsFooter(
                    releaseDate = album.releaseDate,
                    copyright = album.copyright,
                    url = album.url,
                    context = context
                )
            }
        }
        is State.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
```

## Demo

https://drive.google.com/file/d/1H1WJJZaB1alht9HeZR600vF8ABovFrOl/view?usp=sharing
