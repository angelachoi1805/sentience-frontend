# 🧠 Sentience

Sentience is a mental health support Android application designed to help users better understand themselves through psychological tests, informative posts, and an empathetic AI chatbot.

## 📱 Features

### 🤖 AI Therapist Chatbot
- Powered by custom NLP models trained on therapist-like dialogues.
- Understands user queries and responds empathetically.
- Great for casual conversation, emotional support, or mental health guidance.

### 📝 Psychological Tests
- Take scientifically inspired mental health tests.
- Track your scores and view previous results in the profile section.
- Get personalized insights based on your answers.

### 📚 Terminology Posts
- Browse educational content related to mental health.
- Understand key psychological terms in a simple, accessible format.

## 🔧 Tech Stack

| Layer          | Technology                         |
|----------------|-------------------------------------|
| Language       | Kotlin                              |
| UI Framework   | Jetpack Compose                     |
| Architecture   | MVVM                                |
| Backend        | FastAPI (Python)                    |
| ML/NLP         | Custom-trained models using Hugging Face |
| Database       | Firebase / PostgreSQL (depending on config) |
| State Handling | StateFlow, LiveData                 |
| Network        | Retrofit / Ktor Client              |

## 📐 Architecture

- **Frontend (Android)**: MVVM pattern with reactive UI using Jetpack Compose.
- **Backend (Python)**: Handles user data, test results, and AI responses.
- **AI Core**: Processes text prompts and generates contextual, therapeutic replies.

## 🖌️ UI/UX Highlights

- Light and minimal design with calm colors.
- Intuitive navigation between Chat, Tests, and Terminology.
- Chat bubbles for natural dialogue flow.
- Responsive layouts across screen sizes.

## 🚧 Challenges

- Integrating AI responses into real-time chat with consistent state updates.
- Managing test scoring logic and syncing with backend.
- Designing the chatbot to balance empathy and accuracy.

## 🌱 Future Improvements

- Add voice-to-text input for the chatbot.
- Expand the database of psychological terms and definitions.
- Enable localized support (Uzbek, Russian, etc.).
- Offline mode for viewing saved posts and results.

## ✅ Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/sentience-app.git