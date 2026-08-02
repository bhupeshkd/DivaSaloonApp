package com.example.mytestapplication.ui

import androidx.annotation.DrawableRes
import com.example.mytestapplication.R

data class ServiceInfo(
    val id: String,
    val title: String,
    val price: String,
    val ratingLabel: String,
    val duration: String,
    val description: String,
    val shortDescription: String,
    @DrawableRes val imageRes: Int,
    val included: List<String>,
    val notIncluded: List<String>,
)

object ServiceCatalog {
    val bridalMakeup = ServiceInfo(
        id = "bridal_makeup",
        title = "Premium Bridal Makeup",
        price = "$150",
        ratingLabel = "4.9 (128 Reviews)",
        duration = "90 mins",
        description = "Experience flawless, long-lasting beauty for your special day. Our premium bridal makeup service includes a thorough consultation, skin preparation, and expert application using luxury cosmetics tailored to enhance your natural features.",
        shortDescription = "Full face makeup, contouring, and premium lashes.",
        imageRes = R.drawable.service_bridal,
        included = listOf(
            "Skin analysis and preparation",
            "Premium luxury cosmetic products",
            "False eyelash application (optional)",
            "Setting spray for 24h wear",
        ),
        notIncluded = listOf(
            "Hair styling (booked separately)",
            "Trial session prior to the event",
            "Travel fees outside city limits",
        ),
    )

    val glowFacial = ServiceInfo(
        id = "glow_facial",
        title = "Glow Facial Package",
        price = "$89",
        ratingLabel = "4.8 (96 Reviews)",
        duration = "60 mins",
        description = "Revive dull skin with a deep-cleansing facial designed to restore radiance. We customize every step to your skin type, from gentle exfoliation to hydrating masks that leave you with a fresh, luminous glow.",
        shortDescription = "Deep cleanse, exfoliation, and hydrating mask.",
        imageRes = R.drawable.cat_facial,
        included = listOf(
            "Skin type consultation",
            "Deep cleanse and exfoliation",
            "Custom hydrating mask",
            "Moisturizer and SPF finish",
        ),
        notIncluded = listOf(
            "Chemical peels or extractions add-ons",
            "Home skincare product kit",
            "Travel fees outside city limits",
        ),
    )

    val silkHairSpa = ServiceInfo(
        id = "silk_hair_spa",
        title = "Silk Hair Spa Package",
        price = "$120",
        ratingLabel = "4.9 (112 Reviews)",
        duration = "75 mins",
        description = "Nourish and restore your hair with our signature silk spa ritual. Enjoy a scalp massage, deep conditioning treatment, and silk-smooth finish that leaves hair soft, shiny, and beautifully managed.",
        shortDescription = "Scalp massage, conditioning, and silk-smooth finish.",
        imageRes = R.drawable.cat_hair,
        included = listOf(
            "Scalp analysis and massage",
            "Deep conditioning treatment",
            "Steam therapy session",
            "Blow-dry styling finish",
        ),
        notIncluded = listOf(
            "Hair color or highlights",
            "Keratin or protein treatments",
            "Travel fees outside city limits",
        ),
    )

    val deepTissue = ServiceInfo(
        id = "deep_tissue",
        title = "Deep Tissue Package",
        price = "$95",
        ratingLabel = "4.7 (84 Reviews)",
        duration = "60 mins",
        description = "Release tension and ease muscle stiffness with a focused deep tissue massage. Our therapists use firm pressure and targeted techniques to melt away knots and restore comfort after long days or workouts.",
        shortDescription = "Focused pressure massage for tension relief.",
        imageRes = R.drawable.cat_massage,
        included = listOf(
            "Consultation for problem areas",
            "Full-body deep tissue massage",
            "Aromatherapy oil selection",
            "Post-session recovery tips",
        ),
        notIncluded = listOf(
            "Hot stone or cupping add-ons",
            "Sports taping or physio care",
            "Travel fees outside city limits",
        ),
    )

    val signatureBalayage = ServiceInfo(
        id = "signature_balayage",
        title = "Signature Balayage",
        price = "$180",
        ratingLabel = "4.9 (156 Reviews)",
        duration = "120 mins",
        description = "Achieve sun-kissed dimension with our signature balayage. Soft hand-painted highlights blend seamlessly for a natural, luminous finish tailored to your hair tone and lifestyle.",
        shortDescription = "Hand-painted highlights for a luminous finish.",
        imageRes = R.drawable.cat_hair,
        included = listOf(
            "Color consultation",
            "Custom balayage application",
            "Toner and gloss finish",
            "Blow-dry styling",
        ),
        notIncluded = listOf(
            "Full bleach or root touch-up",
            "Keratin treatment",
            "Travel fees outside city limits",
        ),
    )

    val hotStone = ServiceInfo(
        id = "hot_stone",
        title = "Hot Stone Massage",
        price = "$120",
        ratingLabel = "4.8 (102 Reviews)",
        duration = "90 mins",
        description = "Melt away stress with heated basalt stones and flowing massage techniques. Perfect for deep relaxation, circulation, and easing stubborn muscle tightness.",
        shortDescription = "Heated stones with full-body relaxation massage.",
        imageRes = R.drawable.cat_massage,
        included = listOf(
            "Heated basalt stone therapy",
            "Full-body oil massage",
            "Aromatherapy selection",
            "Relaxation stretch finish",
        ),
        notIncluded = listOf(
            "Deep tissue add-on",
            "Cupping therapy",
            "Travel fees outside city limits",
        ),
    )

    val swedishMassage = ServiceInfo(
        id = "swedish_massage",
        title = "Signature Swedish Massage",
        price = "$120",
        ratingLabel = "4.9 (140 Reviews)",
        duration = "60 mins",
        description = "Unwind with classic Swedish massage techniques designed to improve circulation, ease tension, and leave you deeply relaxed.",
        shortDescription = "Classic full-body relaxation massage.",
        imageRes = R.drawable.cat_massage,
        included = listOf(
            "Full-body Swedish massage",
            "Aromatherapy oil selection",
            "Pressure customized to you",
            "Relaxation stretch finish",
        ),
        notIncluded = listOf(
            "Hot stone add-on",
            "Deep tissue upgrade",
            "Travel fees outside city limits",
        ),
    )

    val all: List<ServiceInfo> = listOf(
        bridalMakeup,
        glowFacial,
        silkHairSpa,
        deepTissue,
        signatureBalayage,
        hotStone,
        swedishMassage,
    )

    private val byFeaturedTitle = mapOf(
        "Bridal Makeup" to bridalMakeup,
        "Premium Bridal Makeup" to bridalMakeup,
        "Glow Facial" to glowFacial,
        "Silk Hair Spa" to silkHairSpa,
        "Deep Tissue" to deepTissue,
        "Deep Tissue Massage" to deepTissue,
        "Signature Balayage" to signatureBalayage,
        "Hot Stone Massage" to hotStone,
        "Hot Stone" to hotStone,
        "Signature Swedish Massage" to swedishMassage,
    )

    fun findByFeaturedTitle(title: String): ServiceInfo? =
        byFeaturedTitle.entries
            .firstOrNull { it.key.equals(title, ignoreCase = true) }
            ?.value
}
