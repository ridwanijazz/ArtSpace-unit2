package lat.pam.artspace_unit2

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceApp()
        }
    }
}

data class Artwork(
    val title: String,
    val artist: String,
    val year: String,
    val imageResId: Int
)

@Composable
fun ArtSpaceApp() {
    // Daftar karya seni
    val artworks = listOf(
        Artwork("The Birth of Venus", "Sandro Botticelli", "1486", R.drawable.thebirth),
        Artwork("Mona Lisa", "Leonardo da Vinci", "1519", R.drawable.monalisa),
        Artwork("The Scream", "Edvard Munch", "1893", R.drawable.scream)
    )

    // State untuk melacak indeks karya seni yang sedang ditampilkan
    var currentIndex by remember { mutableStateOf(0) }

    // Fungsi untuk beralih ke karya seni berikutnya
    fun showNextArtwork() {
        currentIndex = (currentIndex + 1) % artworks.size
    }

    // Fungsi untuk beralih ke karya seni sebelumnya
    fun showPreviousArtwork() {
        currentIndex = if (currentIndex - 1 < 0) artworks.size - 1 else currentIndex - 1
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // Tampilan lanskap untuk layar lebih besar
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArtworkImage(
                imageResId = artworks[currentIndex].imageResId,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ArtworkDescription(
                    title = artworks[currentIndex].title,
                    artist = artworks[currentIndex].artist,
                    year = artworks[currentIndex].year
                )
                Spacer(modifier = Modifier.height(16.dp))
                NavigationButtons(
                    onPreviousClick = { showPreviousArtwork() },
                    onNextClick = { showNextArtwork() }
                )
            }
        }
    } else {
        // Tampilan potret
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ArtworkImage(
                imageResId = artworks[currentIndex].imageResId,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            ArtworkDescription(
                title = artworks[currentIndex].title,
                artist = artworks[currentIndex].artist,
                year = artworks[currentIndex].year,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            NavigationButtons(
                onPreviousClick = { showPreviousArtwork() },
                onNextClick = { showNextArtwork() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ArtworkImage(imageResId: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shadowElevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Gambar karya seni",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp)
        )
    }
}

@Composable
fun ArtworkDescription(title: String, artist: String, year: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFFEEEEF0),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = artist,
                fontSize = 15.sp,
                color = Color.DarkGray
            )
            Text(
                text = "($year)",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun NavigationButtons(onPreviousClick: () -> Unit, onNextClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onPreviousClick) {
            Text("Previous")
        }
        Button(onClick = onNextClick) {
            Text("Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceApp()
}
