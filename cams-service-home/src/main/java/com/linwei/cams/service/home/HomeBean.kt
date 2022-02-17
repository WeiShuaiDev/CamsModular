package com.linwei.cams.service.home

data class HomeBean(
    val curPage: Int, val datas: HomeDataBean, val offset: Int, val over: Boolean,
    val pageCount: Int, val size: Int, val total: Int
) {

    data class HomeDataBean(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val host: Boolean,
        val id: Long,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<HomeTagBean>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    ) {
        data class HomeTagBean(val name: String, val url: String)
    }
}