package com.currencyconverter.core.data.utils

val currencyToCountryMap = mapOf(
    "AED" to "AE", // United Arab Emirates Dirham
    "AFN" to "AF", // Afghan Afghani
    "ALL" to "AL", // Albanian Lek
    "AMD" to "AM", // Armenian Dram
    "ANG" to "AN", // Netherlands Antillean Guilder
    "AOA" to "AO", // Angolan Kwanza
    "ARS" to "AR", // Argentine Peso
    "AUD" to "AU", // Australian Dollar
    "AWG" to "AW", // Aruban Florin
    "AZN" to "AZ", // Azerbaijani Manat
    "BAM" to "BA", // Bosnia-Herzegovina Convertible Mark
    "BBD" to "BB", // Barbadian Dollar
    "BDT" to "BD", // Bangladeshi Taka
    "BGN" to "BG", // Bulgarian Lev
    "BHD" to "BH", // Bahraini Dinar
    "BIF" to "BI", // Burundian Franc
    "BMD" to "BM", // Bermudan Dollar
    "BND" to "BN", // Brunei Dollar
    "BOB" to "BO", // Bolivian Boliviano
    "BRL" to "BR", // Brazilian Real
    "BSD" to "BS", // Bahamian Dollar
    "BTC" to "BT", // Bitcoin
    "BTN" to "BT", // Bhutanese Ngultrum
    "BWP" to "BW", // Botswanan Pula
    "BYN" to "BY", // Belarusian Ruble
    "BZD" to "BZ", // Belize Dollar
    "CAD" to "CA", // Canadian Dollar
    "CDF" to "CD", // Congolese Franc
    "CHF" to "CH", // Swiss Franc
    "CLF" to "CL", // Chilean Unit of Account (UF)
    "CLP" to "CL", // Chilean Peso
    "CNH" to "CN", // Chinese Yuan (Offshore)
    "CNY" to "CN", // Chinese Yuan
    "COP" to "CO", // Colombian Peso
    "CRC" to "CR", // Costa Rican Colón
    "CUC" to "CU", // Cuban Convertible Peso
    "CUP" to "CU", // Cuban Peso
    "CVE" to "CV", // Cape Verdean Escudo
    "CZK" to "CZ", // Czech Republic Koruna
    "DJF" to "DJ", // Djiboutian Franc
    "DKK" to "DK", // Danish Krone
    "DOP" to "DO", // Dominican Peso
    "DZD" to "DZ", // Algerian Dinar
    "EGP" to "EG", // Egyptian Pound
    "ERN" to "ER", // Eritrean Nakfa
    "ETB" to "ET", // Ethiopian Birr
    "EUR" to "EU", // Euro
    "FJD" to "FJ", // Fijian Dollar
    "FKP" to "FK", // Falkland Islands Pound
    "GBP" to "GB", // British Pound Sterling
    "GEL" to "GE", // Georgian Lari
    "GGP" to "GG", // Guernsey Pound
    "GHS" to "GH", // Ghanaian Cedi
    "GIP" to "GI", // Gibraltar Pound
    "GMD" to "GM", // Gambian Dalasi
    "GNF" to "GN", // Guinean Franc
    "GTQ" to "GT", // Guatemalan Quetzal
    "GYD" to "GY", // Guyanaese Dollar
    "HKD" to "HK", // Hong Kong Dollar
    "HNL" to "HN", // Honduran Lempira
    "HRK" to "HR", // Croatian Kuna
    "HTG" to "HT", // Haitian Gourde
    "HUF" to "HU", // Hungarian Forint
    "IDR" to "ID", // Indonesian Rupiah
    "ILS" to "IL", // Israeli New Sheqel
    "IMP" to "IM", // Manx pound
    "INR" to "IN", // Indian Rupee
    "IQD" to "IQ", // Iraqi Dinar
    "IRR" to "IR", // Iranian Rial
    "ISK" to "IS", // Icelandic Króna
    "JEP" to "JE", // Jersey Pound
    "JMD" to "JM", // Jamaican Dollar
    "JOD" to "JO", // Jordanian Dinar
    "JPY" to "JP", // Japanese Yen
    "KES" to "KE", // Kenyan Shilling
    "KGS" to "KG", // Kyrgystani Som
    "KHR" to "KH", // Cambodian Riel
    "KMF" to "KM", // Comorian Franc
    "KPW" to "KP", // North Korean Won
    "KRW" to "KR", // South Korean Won
    "KWD" to "KW", // Kuwaiti Dinar
    "KYD" to "KY", // Cayman Islands Dollar
    "KZT" to "KZ", // Kazakhstani Tenge
    "LAK" to "LA", // Laotian Kip
    "LBP" to "LB", // Lebanese Pound
    "LKR" to "LK", // Sri Lankan Rupee
    "LRD" to "LR", // Liberian Dollar
    "LSL" to "LS", // Lesotho Loti
    "LYD" to "LY", // Libyan Dinar
    "MAD" to "MA", // Moroccan Dirham
    "MDL" to "MD", // Moldovan Leu
    "MGA" to "MG", // Malagasy Ariary
    "MKD" to "MK", // Macedonian Denar
    "MMK" to "MM", // Myanma Kyat
    "MNT" to "MN", // Mongolian Tugrik
    "MOP" to "MO", // Macanese Pataca
    "MRU" to "MR", // Mauritanian Ouguiya
    "MUR" to "MU", // Mauritian Rupee
    "MVR" to "MV", // Maldivian Rufiyaa
    "MWK" to "MW", // Malawian Kwacha
    "MXN" to "MX", // Mexican Peso
    "MYR" to "MY", // Malaysian Ringgit
    "MZN" to "MZ", // Mozambican Metical
    "NAD" to "NA", // Namibian Dollar
    "NGN" to "NG", // Nigerian Naira
    "NIO" to "NI", // Nicaraguan Córdoba
    "NOK" to "NO", // Norwegian Krone
    "NPR" to "NP", // Nepalese Rupee
    "NZD" to "NZ", // New Zealand Dollar
    "OMR" to "OM", // Omani Rial
    "PAB" to "PA", // Panamanian Balboa
    "PEN" to "PE", // Peruvian Nuevo Sol
    "PGK" to "PG", // Papua New Guinean Kina
    "PHP" to "PH", // Philippine Peso
    "PKR" to "PK", // Pakistani Rupee
    "PLN" to "PL", // Polish Zloty
    "PYG" to "PY", // Paraguayan Guarani
    "QAR" to "QA", // Qatari Rial
    "RON" to "RO", // Romanian Leu
    "RSD" to "RS", // Serbian Dinar
    "RUB" to "RU", // Russian Ruble
    "RWF" to "RW", // Rwandan Franc
    "SAR" to "SA", // Saudi Riyal
    "SBD" to "SB", // Solomon Islands Dollar
    "SCR" to "SC", // Seychellois Rupee
    "SDG" to "SD", // Sudanese Pound
    "SEK" to "SE", // Swedish Krona
    "SGD" to "SG", // Singapore Dollar
    "SHP" to "SH", // Saint Helena Pound
    "SLL" to "SL", // Sierra Leonean Leone
    "SOS" to "SO", // Somali Shilling
    "SRD" to "SR", // Surinamese Dollar
    "SSP" to "SS", // South Sudanese Pound
    "STD" to "ST", // São Tomé and Príncipe Dobra (pre-2018)
    "STN" to "ST", // São Tomé and Príncipe Dobra
    "SVC" to "SV", // Salvadoran Colón
    "SYP" to "SY", // Syrian Pound
    "SZL" to "SZ", // Swazi Lilangeni
    "THB" to "TH", // Thai Baht
    "TJS" to "TJ", // Tajikistani Somoni
    "TMT" to "TM", // Turkmenistani Manat
    "TND" to "TN", // Tunisian Dinar
    "TOP" to "TO", // Tongan Pa'anga
    "TRY" to "TR", // Turkish Lira
    "TTD" to "TT", // Trinidad and Tobago Dollar
    "TWD" to "TW", // New Taiwan Dollar
    "TZS" to "TZ", // Tanzanian Shilling
    "UAH" to "UA", // Ukrainian Hryvnia
    "UGX" to "UG", // Ugandan Shilling
    "USD" to "US", // United States Dollar
    "UYU" to "UY", // Uruguayan Peso
    "UZS" to "UZ", // Uzbekistan Som
    "VEF" to "VE", // Venezuelan Bolívar Fuerte (Old)
    "VES" to "VE", // Venezuelan Bolívar Soberano
    "VND" to "VN", // Vietnamese Dong
    "VUV" to "VU", // Vanuatu Vatu
    "WST" to "WS", // Samoan Tala
    "XAF" to "CF", // CFA Franc BEAC
    "XAG" to "AG", // Silver Ounce
    "XAU" to "AU", // Gold Ounce
    "XCD" to "DM", // East Caribbean Dollar
    "XDR" to "XDR", // Special Drawing Rights
    "XOF" to "BJ", // CFA Franc BCEAO
    "XPD" to "PD", // Palladium Ounce
    "XPF" to "PF", // CFP Franc
    "XPT" to "PT", // Platinum Ounce
    "YER" to "YE", // Yemeni Rial
    "ZAR" to "ZA", // South African Rand
    "ZMW" to "ZM", // Zambian Kwacha
    "ZWL" to "ZW"  // Zimbabwean Dollar
)
