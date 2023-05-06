package com.example.a5046ass2_project.RecycleView.model

class CourseResult (var unit: String, var mark: Int){
    companion object {
        fun initializeResultList(): MutableList<CourseResult> {
            var results: MutableList<CourseResult> = ArrayList()
            results.add(CourseResult("FIT5046", 87))
            results.add(CourseResult("FIT5152", 77))
            return results
        }
    }
}