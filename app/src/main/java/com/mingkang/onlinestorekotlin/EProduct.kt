package com.mingkang.onlinestorekotlin

class EProduct {
    var id: Int
    var name: String
    var price: Int
    var brand: String
    var pictureName: String

    constructor(id:Int, name:String, price:Int, brand:String, picture: String){
        this.id = id
        this.brand = brand
        this.price = price
        this.pictureName = picture
        this.name = name
    }
}