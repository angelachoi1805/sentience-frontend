package com.example.sentience.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TermsAndConditionsScreen(
    onNavigateBack: (Boolean) -> Unit,
    onAcceptTerms: () -> Unit
) {
    var accepted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Sentience",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        Text(
            text = "Terms & Conditions",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Content Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // About Section
                SectionHeader("About Sentience (Educational Use)")
                SectionContent("Sentience is a university student project app that uses artificial intelligence to simulate emotionally intelligent conversations for mental wellness, and also offers self-assessment tools and educational content to help you reflect on your emotions. Please note that Sentience is provided for educational demonstration purposes only and is not a commercial product or professional service. By using the Sentience app, you agree to these Terms & Conditions.")

                // Therapy Section
                SectionHeader("Not a Substitute for Therapy or Medical Advice")
                SectionContent("Sentience is not a licensed therapist or medical professional. Any conversations or advice provided by the AI are for general support and informational purposes only and are not a substitute for professional therapy, counseling, diagnosis, or medical advice. If you are experiencing a mental health crisis, severe distress, or have thoughts of harming yourself or others, do not rely on this app. Seek immediate help from a qualified mental health professional or contact emergency services or a crisis hotline in your area.")

                // User Responsibilities Section
                SectionHeader("User Responsibilities")
                SectionContent("Using Sentience comes with certain responsibilities for you as the user:")
                BulletPoint("Use for Self-Help Only: Use Sentience for general well-being support and self-reflection. Do not use it for emergencies or urgent mental health needs.")
                BulletPoint("Personal Judgment: Consider any suggestions from the app thoughtfully, as you are responsible for your own decisions and actions. Seek professional guidance for important or serious issues when needed.")
                BulletPoint("Respectful Use: Use the app respectfully. Do not input content that is illegal, abusive, or inappropriate. Misusing the AI may limit its effectiveness for you.")

                // Privacy Section
                SectionHeader("Data Privacy & Confidentiality")
                SectionContent("We understand the importance of your privacy. Sentience is designed to prioritize data confidentiality:")
                BulletPoint("No Unnecessary Data Collection: Sentience does not require you to provide personal information or create an account to use its core features.")
                BulletPoint("Confidential Conversations: Your conversations and self-assessment results are kept private and are not shared with third parties. If any data is stored to improve the app or for academic evaluation, it will be anonymized and handled securely.")
                BulletPoint("Security: We strive to protect your data, but no digital system is 100% secure. Please use discretion when sharing sensitive information and understand that you do so at your own risk.")

                // No Guarantees Section
                SectionHeader("No Guarantees - Use at Your Own Risk")
                SectionContent("Sentience is provided as-is as a demonstration project, and we make no guarantees about the accuracy, completeness, or usefulness of the AI's responses or content. The creators and associated institutions are not responsible for any outcomes, losses, or damages resulting from your use of the app. Use Sentience at your own risk.")

                // Acceptance Section
                SectionHeader("Acceptance of Terms")
                SectionContent("By using Sentience, you acknowledge that you have read and understood these Terms & Conditions. We may update these terms in the future and will notify you of major changes in the app. Continuing to use Sentience after an update means you accept the new terms. If you do not agree with these terms, please do not use the app.")
            }
        }

        // Checkbox and Buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = accepted,
                        onCheckedChange = { accepted = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                    Text(
                        text = "I have read and agree to the Terms & Conditions",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigateBack(accepted) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Back",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp
                            )
                        )
                    }

                    Button(
                        onClick = {
                            if (accepted) {
                                onAcceptTerms()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = accepted,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            "Accept",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        ),
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
private fun SectionContent(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        ),
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun BulletPoint(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            ),
            modifier = Modifier.weight(1f)
        )
    }
} 