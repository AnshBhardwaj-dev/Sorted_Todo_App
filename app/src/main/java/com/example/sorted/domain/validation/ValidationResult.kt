package com.example.sorted.domain.validation

sealed class ValidationResult {
    data class Success(
        val message: String
    ) : ValidationResult()

    data class Error(
        val titleError: String? = null,
        val dueDateError: String? = null
    ) : ValidationResult()
}