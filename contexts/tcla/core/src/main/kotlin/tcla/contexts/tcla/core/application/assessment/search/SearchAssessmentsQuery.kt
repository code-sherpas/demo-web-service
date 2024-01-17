package tcla.contexts.tcla.core.application.assessment.search

import tcla.libraries.search.ManyValuesFilter

data class SearchAssessmentsQuery(val filters: List<ManyValuesFilter<String, String>> = listOf())
