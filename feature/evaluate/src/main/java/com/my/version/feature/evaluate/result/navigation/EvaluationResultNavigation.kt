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
            similarity = evaluationResult.similarity,
            createdDate = evaluationResult.date,
            mostSimilarPeriod = evaluationResult.mostSimilarPeriod,
            leastSimilarPeriod = evaluationResult.leastSimilarPeriod,
            timeLength = evaluationResult.timeLength,
            coverUrl = evaluationResult.coverUrl,
            recordUrl = evaluationResult.recordUrl,
            imageUrl = evaluationResult.imageUrl
        )
    }
}


@Serializable
data class EvaluationResult(
    val evaluationDetailJson: String
) : Route