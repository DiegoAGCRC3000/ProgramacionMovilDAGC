package com.example.notes.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.R
import com.example.notes.model.Reminder
import com.example.notes.ui.theme.ReminderTheme
import java.time.format.TextStyle

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReminderList(
    reminders: List<Reminder>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    val mediumPadding = dimensionResource(R.dimen.padding_medium );
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding){
            itemsIndexed(reminders) { index, reminder ->
                ReminderListItem(
                reminder = reminder,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .animateEnterExit(
                        enter = slideInVertically(
                            animationSpec = spring(
                                stiffness = StiffnessVeryLow,
                                dampingRatio = DampingRatioLowBouncy
                            ),
                            initialOffsetY = { it * (index + 1) }
                        )
                    )
            )
            }
        }
    }
}

@Composable
fun ReminderListItem(
    reminder: Reminder,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
)
{
    Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier,
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(modifier = Modifier
                .width(40.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.CenterStart,
            )
            {
                Checkbox(
                    checked = false,
                    onCheckedChange = {}
                )
            }
            Column(modifier = Modifier.weight(1f))
            {
                Text(
                    text = stringResource(reminder.nameRes),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(reminder.descriptionRes),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(71.dp)
                    .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.CenterEnd,
            )
            {
                Column {
                    Text(
                        text = stringResource(reminder.dateRes),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = stringResource(reminder.hourRes),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.End
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(reminder.status),
                        style = MaterialTheme.typography.labelMedium.copy(color = Color.Red),
                        textAlign = TextAlign.End,
                        fontStyle = FontStyle.Italic,
                    )
                }
                // Imagen pendiente
                // Image(painter = , contentDescription = )
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReminderPreview()
{
    val reminder = Reminder(
        R.string.reminder1,
        R.string.desc_reminder1,
        R.string.reminder1_status,
        R.string.reminder_date1,
        R.string.reminder1_expiration,
    )
    ReminderTheme {
        ReminderListItem(reminder = reminder)
    }
}

@Preview("Notes List")
@Composable
fun RemindersPreview() {
    ReminderTheme(darkTheme = false) {
        Surface (
            color = MaterialTheme.colorScheme.background
        ) {
            //NotesList(notes = HeroesRepository.heroes)
        }
    }
}