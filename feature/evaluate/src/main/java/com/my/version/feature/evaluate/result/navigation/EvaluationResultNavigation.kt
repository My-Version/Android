package com.my.version.feature.evaluate.result.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.my.version.core.common.navigation.Route
import com.my.version.core.domain.entity.EvaluationDetail
import com.my.version.feature.evaluate.result.EvaluationResultRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun NavController.navigateToEvaluationResult(
    evaluationDetail: EvaluationDetail,
    navOptions: NavOptions? = null
) {
    val evaluationDetailJson = Json.encodeToString(EvaluationDetail.serializer(), evaluationDetail)

    navigate(
        route = EvaluationResult(evaluationDetailJson),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.evaluationResultScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<EvaluationResult> { backStackEntry ->
        val json = backStackEntry.toRoute<EvaluationResult>().evaluationDetailJson
        val evaluationResult = Json.decodeFromString<EvaluationDetail>(json)

        EvaluationResultRoute(
            modifier = modifier,
            navigateUp = navigateUp,
            title = evaluationResult.title,
            createdDate = evaluationResult.date,
            coverUrl = evaluationResult.coverUrl,
            recordUrl = evaluationResult.recordUrl,
            similarity = evaluationResult.similarity ?: 0,
            mostSimilarPeriod = evaluationResult.mostSimilarPeriod ?: 0.0,
            leastSimilarPeriod = evaluationResult.leastSimilarPeriod ?: 0.0,
            timeLength = evaluationResult.timeLength ?: 5,
            imageUrl = evaluationResult.imageUrl.orEmpty()
        )
    }
}


@Serializable
data class EvaluationResult(
    val evaluationDetailJson: String
) : Route