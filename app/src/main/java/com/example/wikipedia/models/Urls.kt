package com.example.wikipedia.models

object Urls {
    val BaseUrl = "https://en.wikipedia.org/w/api.php"

    /**
     * This URL helps us get data matching the search terms
     * @param term the term to search
     * @param skip the off-set to skip if paginating
     * @param take the Int that can help us paginate
     * @return the URL to get 'take' number of random articles
     */
    fun getSearchUrl(term: String, skip: Int, take: Int): String {
        return BaseUrl +
                "?action=query" +
                "&formatversion=2" +
                "&generator=prefixsearch" +  // allows us set the search term
                "&gpssearch=$term" +
                "&gpslimit=$take" +
                "&gpsoffset=$skip" +         // if we are to paginate
                "&prop=pageimages|info" +
                "&piprop=thumbnail|url" +
                "&pithumbsize=200" +
                "&pitlimit=$take" +
                "&wptterms=description" +    // terms to be searched
                "&format=json" +
                "&inprop=url"                // needed for detail activity
    }

    /**
     * This URL helps us get random data
     * @param take the Int that can help us paginate
     * @return the URL to get `take` number of random articles
     */
    fun getRandomUrl(take: Int): String {
        return BaseUrl +
                "?action=query" +
                "&format=json" +
                "&formatversion=2" +
                "&generator=random" +
                "&grnnamespace=0" +
                "&prop=pageimages|info" +
                "&grnlimit=$take" +
                "&inprop=url" +
                "&pithumbsize=200"
    }
}