package tech.izak.readqrcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tech.izak.readqrcode.ui.theme.ReadQRCodeTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ReadQRCodeTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          ReadQRCode()
        }
      }
    }
  }
}

data class ReadQRCodeUiState(
  val qrCodeValue: String = "",
)

class ReadQRCodeViewModel : ViewModel() {
  var _uiState = MutableStateFlow(ReadQRCodeUiState())
  val uiState = _uiState.asStateFlow()

  fun onResult(result: ScanIntentResult) {
    _uiState.update {
      it.copy(qrCodeValue = result.contents)
    }
  }
}

@Composable
fun ReadQRCode(readQRCodeViewModel: ReadQRCodeViewModel = viewModel()) {
  val readQRCodeUiState by readQRCodeViewModel.uiState.collectAsState()

  val scanLauncher = rememberLauncherForActivityResult(
    contract = ScanContract(),
    onResult = { readQRCodeViewModel.onResult(it) }
  )

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
      Button(
        contentPadding = PaddingValues(
          start = 20.dp,
          top = 12.dp,
          end = 20.dp,
          bottom = 12.dp
        ),
        onClick = {
          val options = ScanOptions()

          options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
          options.setPrompt("Abra o site e escaneie o QR Code.");

          scanLauncher.launch(options)
        }
      ) {
        Icon(
          Icons.Filled.QrCodeScanner,
          contentDescription = "Favorite",
          modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = "Escanear QR Code")
      }

      if (readQRCodeUiState.qrCodeValue.isNotEmpty()) {
        Text(text = "Resultado:", fontWeight = FontWeight.SemiBold)
        Text(text = readQRCodeUiState.qrCodeValue)
      }
    }
  }
}