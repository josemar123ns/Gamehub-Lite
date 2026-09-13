# Arquitetura Gamehub Lite

## Visão Geral

Gamehub Lite segue a arquitetura **Clean Architecture** combinada com **MVVM** (Model-View-ViewModel) e **Repository Pattern**, garantindo um código limpo, testável e escalável.

## Camadas

### 1. Presentation Layer (UI)
**Localização**: `ui/screens/`, `ui/navigation/`, `ui/theme/`

- Composables (telas)
- Navigation
- Temas e estilos
- Não contém lógica de negócio

```
HomeScreen.kt
    ↓
HomeViewModel (Hilt injected)
    ↓
UseCase/Repository
```

### 2. Domain Layer
**Localização**: `domain/usecase/`, `domain/model/`

- Lógica de negócio pura
- Independente de framework
- Casos de uso (UseCases)
- Modelos de domínio

### 3. Data Layer
**Localização**: `data/repository/`, `data/local/`, `data/remote/`, `data/model/`

#### Local DataSource
- Room Database
- SharedPreferences (via DataStore)
- Cache local

#### Remote DataSource
- Retrofit API calls
- API responses

#### Repository
- Implementa Repository Pattern
- Orquestra Local + Remote DataSources
- Mapeia modelos Data → Domain

## Fluxo de Dados

```
UI (Composable)
    ↓
ViewModel (collect Flow)
    ↓
UseCase (executa lógica)
    ↓
Repository (pega dados)
    ├→ LocalDataSource (Room)
    └→ RemoteDataSource (API)
    ↓
Database Entity / API Response
    ↓
Repository (mapeia para Domain Model)
    ↓
UseCase (retorna Domain Model)
    ↓
ViewModel (emite State)
    ↓
UI (recompõe)
```

## Padrões de Design

### Repository Pattern
Centraliza a lógica de obtenção de dados:

```kotlin
// GameRepository.kt
class GameRepository @Inject constructor(
    private val gameDao: GameDao,
    private val gameApi: GameApi
) {
    fun getGames(): Flow<List<Game>> = flow {
        emit(gameDao.getAll()) // Local primeiro
        emit(gameApi.getGames()) // Depois remoto
    }
}
```

### MVVM with Hilt
ViewModels injetados automaticamente:

```kotlin
// HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {
    val games = repository.getGames()
}

// HomeScreen.kt
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val games by viewModel.games.collectAsState()
}
```

### Dependency Injection (Hilt)

**Módulos**: `di/`
- DatabaseModule
- NetworkModule
- RepositoryModule

```kotlin
// DatabaseModule.kt
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase
        = Room.databaseBuilder(...).build()
}
```

## Estrutura Recomendada para Novas Features

### 1. Criar Model
```kotlin
// data/model/Game.kt
data class GameEntity(...)

// domain/model/Game.kt
data class Game(...)
```

### 2. Criar Data Sources
```kotlin
// data/local/GameDao.kt - Room operations
// data/remote/GameApi.kt - API calls
```

### 3. Criar Repository
```kotlin
// data/repository/GameRepository.kt
class GameRepository(...)
```

### 4. Criar UseCase (opcional)
```kotlin
// domain/usecase/GetGamesUseCase.kt
class GetGamesUseCase @Inject constructor(repository: GameRepository)
```

### 5. Criar ViewModel
```kotlin
// presentation/viewmodel/GameViewModel.kt
@HiltViewModel
class GameViewModel @Inject constructor(...)
```

### 6. Criar Screen
```kotlin
// ui/screens/GameScreen.kt
@Composable
fun GameScreen(viewModel: GameViewModel = hiltViewModel())
```

## Exemplo Completo: Carregar Lista de Jogos

### Step 1: Modelo
```kotlin
@Entity("games")
data class GameEntity(
    @PrimaryKey val id: String,
    val name: String
)

data class Game(val id: String, val name: String)
```

### Step 2: DAO
```kotlin
@Dao
interface GameDao {
    @Query("SELECT * FROM games")
    fun getAll(): Flow<List<GameEntity>>
}
```

### Step 3: Repository
```kotlin
class GameRepository @Inject constructor(
    private val gameDao: GameDao
) {
    fun getGames(): Flow<List<Game>> =
        gameDao.getAll().map { entities ->
            entities.map { it.toGame() }
        }
}
```

### Step 4: ViewModel
```kotlin
@HiltViewModel
class GameViewModel @Inject constructor(
    repository: GameRepository
) : ViewModel() {
    val games = repository.getGames()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
```

### Step 5: Screen
```kotlin
@Composable
fun GameListScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val games by viewModel.games.collectAsState()
    
    LazyColumn {
        items(games) { game ->
            Text(game.name)
        }
    }
}
```

## Boas Práticas

✅ **DO**
- Separar lógica de UI da lógica de negócio
- Usar Flow/StateFlow para reatividade
- Injetar dependências com Hilt
- Testar camada de dados e domain
- Usar sealed classes para estados

❌ **DON'T**
- Fazer network calls direto em Composable
- Passar Context entre camadas
- Usar lógica de negócio em Activity/Fragment
- Muta estado diretamente
- Ignorar tratamento de erro

## Tratamento de Erros

```kotlin
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val exception: Exception) : Result<T>()
    class Loading<T> : Result<T>()
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = map {
    Result.Success(it)
}.catch { e ->
    emit(Result.Error(e as Exception))
}.onStart {
    emit(Result.Loading())
}
```

## Testing

Cada camada deve ter testes:

```kotlin
// Data layer test
@Test
fun `getGames returns games from database`() { }

// Domain layer test
@Test
fun `getGamesUseCase filters invalid games`() { }

// Presentation layer test
@Test
fun `GameViewModel emits games`() { }
```

## Migrações e Versioning

### Room Database
```kotlin
Rom.databaseBuilder(...)
    .addMigrations(MIGRATION_1_2)
    .build()

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE games ADD COLUMN rating REAL")
    }
}
```

## Performance

- ✨ Lazy load em listas
- 🔄 Paginação de dados
- 💾 Cache de imagens
- ⚡ Coroutines para async
- 📦 ProGuard em release

---

**Última atualização**: Setembro 2026
