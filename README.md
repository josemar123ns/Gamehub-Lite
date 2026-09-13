# Gamehub Lite 🎮

Aplicativo nativo Android para gerenciar e descobrir jogos. Otimizado para Poco X8 Pro e outros dispositivos Android 26+.

## 📋 Características

- 📱 Interface moderna com Jetpack Compose
- 🗄️ Banco de dados local com Room
- 🔄 Sincronização de dados com API REST
- 💾 Gerenciamento de jogos instalados
- 🎨 Tema adaptativo (Light/Dark)
- ⚡ Performance otimizada
- 🏗️ Arquitetura MVVM + Clean Code
- 💉 Injeção de dependência com Hilt

## 🛠️ Tecnologias

- **Kotlin** - Linguagem de programação
- **Jetpack Compose** - UI declarativa
- **Room** - Banco de dados local
- **Retrofit** - Cliente HTTP
- **Hilt** - Injeção de dependência
- **Coroutines** - Programação assíncrona
- **Material Design 3** - Design system

## 🚀 Começando

### Pré-requisitos
- Android Studio Giraffe ou superior
- JDK 17+
- Android SDK 26+ (para Poco X8 Pro)

### Instalação

1. Clone o repositório
```bash
git clone https://github.com/josemar123ns/Gamehub-Lite.git
cd Gamehub-Lite
```

2. Abra em Android Studio
```bash
android-studio .
```

3. Sincronize as dependências Gradle

4. Rode na emulador ou dispositivo
```bash
./gradlew installDebug
```

## 📁 Estrutura do Projeto

```
app/src/
├── main/
│   ├── AndroidManifest.xml
│   ├── kotlin/com/gamehub/lite/
│   │   ├── MainActivity.kt
│   │   ├── GamehubApp.kt
│   │   ├── data/
│   │   │   ├── local/          # Room Database
│   │   │   ├── model/          # Data models
│   │   │   └── repository/     # Repository pattern
│   │   ├── di/                 # Dependency Injection
│   │   ├── ui/
│   │   │   ├── navigation/     # Navegação Compose
│   │   │   ├── screens/        # Telas da app
│   │   │   └── theme/          # Temas e estilos
│   │   └── utils/              # Utilitários
│   └── res/
│       ├── drawable/           # Imagens
│       ├── values/             # Strings e temas
│       └── mipmap/             # Ícones
└── test/
    └── ...                     # Testes unitários
```

## 🏗️ Arquitetura

Projeto segue Clean Architecture + MVVM:

```
UI Layer (Compose)
    ↓
Presentation Layer (ViewModel)
    ↓
Domain Layer (UseCases)
    ↓
Data Layer (Repository + DataSources)
```

## 📝 Guia de Desenvolvimento

### Adicionar uma nova tela

1. Crie o arquivo em `ui/screens/NovaScreen.kt`
2. Adicione a rota em `navigation/GamehubNavHost.kt`
3. Crie o ViewModel em `presentation/viewmodel/NovaViewModel.kt`
4. Use Hilt para injetar dependências

### Adicionar uma nova API

1. Defina o modelo em `data/model/`
2. Crie o serviço em `data/remote/`
3. Implemente no Repository
4. Use em ViewModel

## 🔐 Segurança

- Proguard ativado em release
- Permissões declaradas no manifest
- HTTPS para requisições
- Validação de entrada

## 📈 Performance

- Otimizado para minSDK 26 (Android 8.0+)
- Lazy loading de imagens com Coil
- Database queries otimizadas
- Composables eficientes

## 🤝 Contribuindo

1. Crie uma branch feature (`git checkout -b feature/MinhaFeature`)
2. Commit suas mudanças (`git commit -m 'Add nova feature'`)
3. Push para a branch (`git push origin feature/MinhaFeature`)
4. Abra um Pull Request

## 📄 Licença

Distribuído sob a licença MIT.

## 👤 Autor

**josemar123ns**

## 📞 Suporte

Para dúvidas ou problemas, abra uma issue no GitHub.

---

**Status**: Em desenvolvimento 🚀