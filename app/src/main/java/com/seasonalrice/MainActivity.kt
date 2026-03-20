package com.seasonalrice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seasonalrice.ui.theme.SeasonalRiceTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeasonalRiceTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SeasonalFoodApp()
                }
            }
        }
    }
}

data class RecipeRecommendation(
    val name: String,
    val benefit: String,
    val audience: String,
    val ingredients: String,
)

data class SeasonalAdvice(
    val solarTerm: String,
    val dateText: String,
    val weatherHint: String,
    val dailySuggestion: String,
    val recipes: List<RecipeRecommendation>,
)

private fun todayAdvice(now: LocalDate = LocalDate.now()): SeasonalAdvice {
    val formatter = DateTimeFormatter.ofPattern("M月d日")
    return SeasonalAdvice(
        solarTerm = "春分",
        dateText = now.format(formatter),
        weatherHint = "昼夜均分，适合轻补、清润、少油腻。",
        dailySuggestion = "宜养肝，少酸多甘，清淡为主，多吃时蔬和温润汤水。",
        recipes = listOf(
            RecipeRecommendation(
                name = "山药排骨汤",
                benefit = "健脾养胃",
                audience = "工作忙、三餐不规律的人",
                ingredients = "山药、排骨、胡萝卜、生姜"
            ),
            RecipeRecommendation(
                name = "清炒菠菜",
                benefit = "补铁润燥",
                audience = "久坐、容易眼干的人",
                ingredients = "菠菜、蒜末、枸杞"
            ),
            RecipeRecommendation(
                name = "小米南瓜粥",
                benefit = "安神暖胃",
                audience = "晚睡、胃口偏弱的人",
                ingredients = "小米、南瓜、红枣"
            )
        )
    )
}

@Composable
fun SeasonalFoodApp() {
    val advice = todayAdvice()
    val background = Brush.verticalGradient(
        colors = listOf(Color(0xFFF7F4EA), Color(0xFFFFFCF6))
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderCard(advice)
            SuggestionCard(advice.dailySuggestion)
            SectionTitle(title = "今日推荐 2～3 道菜", subtitle = "打开 App 第一眼就知道今天吃什么")
            advice.recipes.forEach { recipe ->
                RecipeCard(recipe)
            }
        }
    }
}

@Composable
private fun HeaderCard(advice: SeasonalAdvice) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.92f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "节气食养",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF355C3A)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "📅 今日节气", color = Color(0xFF647067), fontSize = 14.sp)
                    Text(
                        text = advice.solarTerm,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1F3A24)
                    )
                    Text(text = advice.dateText, color = Color(0xFF8B938C), fontSize = 14.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFE7F2E5))
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Text(text = "🍽", fontSize = 26.sp)
                }
            }
            Text(
                text = "🌤 ${advice.weatherHint}",
                color = Color(0xFF4A5A4C),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun SuggestionCard(suggestion: String) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF7EB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "今日建议",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A4B32)
            )
            Text(
                text = suggestion,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF48614A),
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF253A29)
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF728174)
        )
    }
}

@Composable
private fun RecipeCard(recipe: RecipeRecommendation) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B3020)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TagChip(text = recipe.benefit)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TagChip(text = "适合：${recipe.audience}")
            }
            Text(
                text = "食材：${recipe.ingredients}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF617063)
            )
        }
    }
}

@Composable
private fun TagChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFF3F1DE))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF6F642A)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFCF6)
@Composable
private fun SeasonalFoodPreview() {
    SeasonalRiceTheme {
        SeasonalFoodApp()
    }
}
