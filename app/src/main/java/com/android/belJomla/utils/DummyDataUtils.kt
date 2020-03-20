package com.android.belJomla.utils

import com.android.belJomla.models.LocalizedName
import com.android.belJomla.models.Product

class DummyDataUtils {

    companion object {
        /*fun getDummyProducts():ArrayList<Product>{
            val data = ArrayList<Product>()
            val commonUrls = ArrayList<String>()


            commonUrls.add("https://picsum.photos/300/300")
            LoggerUtils.logErrorMessage(this, "catMap : " + com.android.belJomla.utils.Constants.categMap)



            data.add(
                Product(
                    "1", "Product1 ", commonUrls, "1",
                    "1_2", 22.0, "20 mg", "Small"
                )
            )

            data.add(
                Product(
                    "2", "Product2 With a name that is pretty long.", commonUrls, "1",
                    "1_2", 22.0, "23 oz", "Small"
                )
            )

            data.add(
                Product(
                    "3", "Product3", commonUrls, "1",
                    "1_2", 28.0, "21 mg", "Small"
                )
            )

            data.add(
                Product(
                    "4", "Product4", commonUrls, "1",
                    "1_2", 2.0, "2.2 mg", "Small"
                )
            )


            data.add(
                Product(
                    "1", "Product1 ", commonUrls, "1",
                    "1_3", 22.0, "20 mg", "Small"
                )
            )

            data.add(
                Product(
                    "2", "Product2 With a name that is pretty long.", commonUrls, "1",
                    "1_3", 22.0, "23 oz", "Small"
                )
            )


            data.add(
                Product(
                    "3", "Product3", commonUrls, "1",
                    "1_4", 28.0, "21 mg", "Small"
                )
            )

            data.add(
                Product(
                    "4", "Product4", commonUrls, "1",
                    "1_5", 2.0, "2.2 mg", "Small"
                )
            )


            data.add(
                Product(
                    "5", "Product5", commonUrls, "2",
                    "2_2", 222.0, "200 mg", "Small"
                )
            )


            data.add(
                Product(
                    "6", "Product6", commonUrls, "2",
                    "2_2", 342.0, "155 mg", "Small"
                )
            )

            data.add(
                Product(
                    "7", "Product7", commonUrls, "2",
                    "2_3", 12.0, "32 mg", "Small"
                )
            )

            data.add(
                Product(
                    "8", "Product8", commonUrls, "2",
                    "2_3", 43.0, "24 mg", "Small"
                )
            )


            data.add(
                Product(
                    "5", "Product5", commonUrls, "2",
                    "2_4", 222.0, "200 mg", "Small"
                )
            )


            data.add(
                Product(
                    "6", "Product6", commonUrls, "2",
                    "2_5", 342.0, "155 mg", "Small"
                )
            )


            data.add(
                Product(
                    "7", "Product7", commonUrls, "2",
                    "2_5", 12.0, "32 mg", "Small"
                )
            )

            data.add(
                Product(
                    "8", "Product8", commonUrls, "2",
                    "2_5", 43.0, "24 mg", "Small"
                )
            )


            data.add(
                Product(
                    "9", "Product9", commonUrls, "3",
                    "3_2", 66.0, "87 mg", "Small"
                )
            )

            data.add(
                Product(
                    "10", "Product10", commonUrls, "3",
                    "3_3", 1.0, "32g", "Small"
                )
            )

            data.add(
                Product(
                    "11", "Product11", commonUrls, "3",
                    "3_3", 12.4, "43 mg", "Small"
                )
            )

            data.add(
                Product(
                    "12", "Product12", commonUrls, "3",
                    "3_3", 22.99, "29 mg", "Small"
                )
            )


            data.add(
                Product(
                    "9", "Product9", commonUrls, "3",
                    "3_4", 66.0, "87 mg", "Small"
                )
            )

            data.add(
                Product(
                    "10", "Product10", commonUrls, "3",
                    "3_4", 1.0, "32g", "Small"
                )
            )


            data.add(
                Product(
                    "11", "Product11", commonUrls, "3",
                    "3_5", 12.4, "43 mg", "Small"
                )
            )

            data.add(
                Product(
                    "12", "Product12", commonUrls, "3",
                    "3_5", 22.99, "29 mg", "Small"
                )
            )

            return data
        }*/
        fun getLocalizedDummyProducts():ArrayList<Product> {
            val data = ArrayList<Product>()
            val commonUrls = ArrayList<String>()


            commonUrls.add("https://picsum.photos/300/300")
            LoggerUtils.logErrorMessage(this, "catMap : " + com.android.belJomla.utils.Constants.categMap)



            data.add(
                Product(
                    "1", LocalizedName("Products1","المنتج 1"), commonUrls, "1",
                    "1_2", 1.8, LocalizedName("500 g","500 جرام"), LocalizedName("small","صغير")
                )
            )
            data.add(
                Product(
                    "2", LocalizedName("Products2","المنتج 2"), commonUrls, "1",
                    "1_2", 1.0, LocalizedName("500 g","500 جرام"), LocalizedName("medium","وسط")
                )
            )
            data.add(
                Product(
                    "3", LocalizedName("Products3","المنتج 3"), commonUrls, "1",
                    "1_3", 36.0, LocalizedName("500 g","500 جرام"), LocalizedName("big","كبير")
                )
            )
            data.add(
                Product(
                    "4", LocalizedName("Products4","المنتج 4"), commonUrls, "1",
                    "1_3", 2.0, LocalizedName("500 g","500 جرام"), LocalizedName("medium","وسط")
                )
            )
            data.add(
                Product(
                    "5", LocalizedName("Products5","المنتج 5"), commonUrls, "2",
                    "2_2", 95.0, LocalizedName("500 g","500 جرام"), LocalizedName("big","كبير")
                )
            )
            data.add(
                Product(
                    "6", LocalizedName("Products6","المنتج 6"), commonUrls, "2",
                    "2_2", 41.0, LocalizedName("500 g","500 جرام"), LocalizedName("medium","وسط")
                )
            )
            data.add(
                Product(
                    "7", LocalizedName("Products7","المنتج 7"), commonUrls, "2",
                    "2_3", 572.0, LocalizedName("500 g","500 جرام"), LocalizedName("small","صغير")
                )
            )
            data.add(
                Product(
                    "8", LocalizedName("Products8","المنتج 8"), commonUrls, "2",
                    "2_5", 41.0, LocalizedName("500 g","500 جرام"), LocalizedName("small","صغير")
                )
            )
            data.add(
                Product(
                    "9", LocalizedName("Products9","المنتج 9"), commonUrls, "3",
                    "3_3", 14.0, LocalizedName("500 g","500 جرام"), LocalizedName("small","صغير")
                )
            )
            data.add(
                Product(
                    "10", LocalizedName("Products10","المنتج 10"), commonUrls, "3",
                    "3_3", 72.0, LocalizedName("500 g","500 جرام"), LocalizedName("small","صغير")
                )
            )
            data.add(
                Product(
                    "11", LocalizedName("Products11","المنتج 11"), commonUrls, "3",
                    "3_2", 22.0, LocalizedName("500 g","500 جرام"), LocalizedName("small","صغير")
                )
            )
            data.add(
                Product(
                    "12", LocalizedName("Products12","المنتج 12"), commonUrls, "3",
                    "3_2", 41.0, LocalizedName("500 g","500 جرام"), LocalizedName("small","صغير")
                )
            )




            return data
        }
    }



}