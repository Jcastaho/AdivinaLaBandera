package com.straccion.adivinalabandera.domain.models

import com.straccion.adivinalabandera.R

data class Personaje(
    val imagenId: Int,
    val respuestasCorrectas: String,
    val respuestasIncorrectas: List<String>,
    val caracteristica: String
) {
    object Personajes {
        val listaPersonajes = listOf(
            Personaje(
                imagenId = R.drawable.afganistan,
                respuestasCorrectas = "Afganistan",
                respuestasIncorrectas = listOf("India", "Bahamas", "Belice"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.albania,
                respuestasCorrectas = "Albania",
                respuestasIncorrectas = listOf("Chad", "Camerun", "Butan"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.alemania,
                respuestasCorrectas = "Alemania",
                respuestasIncorrectas = listOf("Armenia", "Bolivia", "China"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.andorra,
                respuestasCorrectas = "Andorra",
                respuestasIncorrectas = listOf("España", "Francia", "Panama"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.angola,
                respuestasCorrectas = "Argelia",
                respuestasIncorrectas = listOf("Sudrafrica", "Bulgaria", "Yemen"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.antigua_y_barbuda,
                respuestasCorrectas = "Antigua y Barbuda",
                respuestasIncorrectas = listOf("Grecia", "Iran", "Lituania"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.arabia_saudita,
                respuestasCorrectas = "Arabia Saudita",
                respuestasIncorrectas = listOf("Malasia", "Marruecos", "Israel"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.argelia,
                respuestasCorrectas = "Argelia",
                respuestasIncorrectas = listOf("Madagascar", "Jordania", "Islas Salomon"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.argentina,
                respuestasCorrectas = "Argentina",
                respuestasIncorrectas = listOf("Chile", "Peru", "Luxemburgo"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.armenia,
                respuestasCorrectas = "Armenia",
                respuestasIncorrectas = listOf("Ecuador", "Mexico", "Malta"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.australia,
                respuestasCorrectas = "Australia",
                respuestasIncorrectas = listOf("Kazajistan", "Italia", "Colombia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.austria,
                respuestasCorrectas = "Austria",
                respuestasIncorrectas = listOf("Noruega", "Nepal", "Reino Unido"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.azerbaijan,
                respuestasCorrectas = "Azerbaijan",
                respuestasIncorrectas = listOf("Polonia", "Samoa", "Siria"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.bahamas,
                respuestasCorrectas = "Bahamas",
                respuestasIncorrectas = listOf("Ucrania", "Uganda", "Rumania"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.bangladesh,
                respuestasCorrectas = "Bangladesh",
                respuestasIncorrectas = listOf("Cabo Verde", "Armenia", "Austria"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.barbados,
                respuestasCorrectas = "Barbados",
                respuestasIncorrectas = listOf("Vietnam", "Tanzania", "Egipto"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.barein,
                respuestasCorrectas = "Barein",
                respuestasIncorrectas = listOf("Guyana", "Japon", "Hungria"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.belgica,
                respuestasCorrectas = "Belgica",
                respuestasIncorrectas = listOf("Afganistan", "Kenia", "Indonesia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.belice,
                respuestasCorrectas = "Belice",
                respuestasIncorrectas = listOf("Lituania", "Jamaica", "Zimbabue"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.benin,
                respuestasCorrectas = "Benin",
                respuestasIncorrectas = listOf("Botsuana", "Vanuatu", "Tuvalu"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.bielorrusia,
                respuestasCorrectas = "Bielorrusia",
                respuestasIncorrectas = listOf("Sudan", "Suiza", "Turkmenistan"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.birmania,
                respuestasCorrectas = "Birmania",
                respuestasIncorrectas = listOf("Togo", "Serbia", "Rusia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.bolivia,
                respuestasCorrectas = "Bolivia",
                respuestasIncorrectas = listOf("Peru", "Chipre", "Surinam"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.bosnia_y_herzegovina,
                respuestasCorrectas = "Bosnia y Herzegovina",
                respuestasIncorrectas = listOf("San Cristobal y Nieves", "Timor Oriental", "Sudan del Sur"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.botsuana,
                respuestasCorrectas = "Botsuana",
                respuestasIncorrectas = listOf("Burundi", "Tailandia", "Maldivas"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.brasil,
                respuestasCorrectas = "Brasil",
                respuestasIncorrectas = listOf("Chile", "Canada", "Nepal"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.brunei,
                respuestasCorrectas = "Brunei",
                respuestasIncorrectas = listOf("Filipinas", "Ghana", "Haiti"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.bulgaria,
                respuestasCorrectas = "Bulgaria",
                respuestasIncorrectas = listOf("Georgia", "Kenia", "Irak"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.burkina_faso,
                respuestasCorrectas = "Burkina Faso",
                respuestasIncorrectas = listOf("Islas Salomon", "Liberia", "Macedonia del Norte"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.burundi,
                respuestasCorrectas = "Burundi",
                respuestasIncorrectas = listOf("Nigeria", "Nepal", "Ruanda"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.butan,
                respuestasCorrectas = "Butan",
                respuestasIncorrectas = listOf("Cuba", "Nauru", "Moldavia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.cabo_verde,
                respuestasCorrectas = "Cabo Verde",
                respuestasIncorrectas = listOf("Republica del Congo", "Somalia", "Trinidad y Tobago"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.camboya,
                respuestasCorrectas = "Camboya",
                respuestasIncorrectas = listOf("Croacia", "Brunei", "Zimbabue"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.camerun,
                respuestasCorrectas = "Camerun",
                respuestasIncorrectas = listOf("Suiza", "Togo", "Uganda"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.canada,
                respuestasCorrectas = "Canada",
                respuestasIncorrectas = listOf("Estados Unidos", "Mexico", "Uruguay"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.catar,
                respuestasCorrectas = "Catar",
                respuestasIncorrectas = listOf("Tunez", "Polonia", "Gambia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.chad,
                respuestasCorrectas = "Chad",
                respuestasIncorrectas = listOf("Fiyi", "Eritrea", "Lesoto"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.chile,
                respuestasCorrectas = "Chile",
                respuestasIncorrectas = listOf("Peru", "Maldivas", "Kenia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.china,
                respuestasCorrectas = "China",
                respuestasIncorrectas = listOf("Japon", "Estonia", "Corea del Norte"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.chipre,
                respuestasCorrectas = "Chipre",
                respuestasIncorrectas = listOf("Libia", "Malta", "Oman"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.colombia,
                respuestasCorrectas = "Colombia",
                respuestasIncorrectas = listOf("Ecuador", "Armenia", "Venezuela"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.comoras,
                respuestasCorrectas = "Comoras",
                respuestasIncorrectas = listOf("Croacia", "Portugal", "Paises Bajos"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.corea_del_norte,
                respuestasCorrectas = "Corea del Norte",
                respuestasIncorrectas = listOf("Corea del Sur", "Mozambique", "Papua Nueva Guinea"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.corea_del_sur,
                respuestasCorrectas ="Corea del Sur",
                respuestasIncorrectas = listOf("Corea del Norte", "Rumania", "Zimbabue"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.costa_de_marfil,
                respuestasCorrectas ="Costa de Marfil",
                respuestasIncorrectas = listOf("Republica Centroafricana", "Serbia", "Tonga"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.costa_rica,
                respuestasCorrectas ="Costa Rica",
                respuestasIncorrectas = listOf("Republica Dominicana", "Sierra Leona", "Islas Marshall"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.croacia,
                respuestasCorrectas ="Croacia",
                respuestasIncorrectas = listOf("Bielorrusia", "Madagascar", "Paraguay"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.cuba,
                respuestasCorrectas ="Cuba",
                respuestasIncorrectas = listOf("Puerto Rico", "Pakistan", "Argelia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.dinamarca,
                respuestasCorrectas ="Dinamarca",
                respuestasIncorrectas = listOf("Albania", "Camerun", "Etiopia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.dominica,
                respuestasCorrectas ="Dominica",
                respuestasIncorrectas = listOf("Bulgaria", "Tunez", "Siria"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.ecuador,
                respuestasCorrectas ="Ecuador",
                respuestasIncorrectas = listOf("Zambia", "Indonesia", "Colombia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.egipto,
                respuestasCorrectas ="Egipto",
                respuestasIncorrectas = listOf("Liberia", "Irak", "Haiti"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.el_salvador,
                respuestasCorrectas ="El Salvador",
                respuestasIncorrectas = listOf("Argentina", "Honduras", "Guatemala"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.emiratos_arabes_unidos,
                respuestasCorrectas ="Emiratos Arabes Unidos",
                respuestasIncorrectas = listOf("Indonesia", "Guayana", "Guinea"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.eritrea,
                respuestasCorrectas ="Eritrea",
                respuestasIncorrectas = listOf("Kenia", "Libia", "Togo"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.eslovaquia,
                respuestasCorrectas ="Eslovequia",
                respuestasIncorrectas = listOf("Eritrea", "Granada", "Chad"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.eslovenia,
                respuestasCorrectas ="Eslovenia",
                respuestasIncorrectas = listOf("Estonia", "Yemen", "Suecia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.espana,
                respuestasCorrectas ="España",
                respuestasIncorrectas = listOf("Colombia", "Francia", "Italia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.estados_unidos,
                respuestasCorrectas ="Estados Unidos",
                respuestasIncorrectas = listOf("Mexico", "Canada", "Australia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.estonia,
                respuestasCorrectas ="Estonia",
                respuestasIncorrectas = listOf("Eritrea", "El Salvador", "Uruguay"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.etiopia,
                respuestasCorrectas ="Etiopia",
                respuestasIncorrectas = listOf("Uzbekistan", "Rumania", "Irlanda"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.fiyi,
                respuestasCorrectas ="Fiyi",
                respuestasIncorrectas = listOf("Irak", "Laos", "Chad"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.filipinas,
                respuestasCorrectas ="Filipinas",
                respuestasIncorrectas = listOf("Malasia", "Dinamarca", "Suiza"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.finlandia,
                respuestasCorrectas ="Finlandia",
                respuestasIncorrectas = listOf("Emiratos Arabes Unidos", "Bosnia y Herzegovina", "Antigua y Barbuda"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.francia,
                respuestasCorrectas ="Francia",
                respuestasIncorrectas = listOf("Italia", "España", "Rusia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.gabon,
                respuestasCorrectas ="Gabon",
                respuestasIncorrectas = listOf("Mauricio", "Nauru", "Niger"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.gambia,
                respuestasCorrectas ="Gambia",
                respuestasIncorrectas = listOf("Fiyi", "Eslovenia", "Belice"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.georgia,
                respuestasCorrectas ="Georgia",
                respuestasIncorrectas = listOf("Alemania", "Rusia", "Croacia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.filipinas,
                respuestasCorrectas ="Filipinas",
                respuestasIncorrectas = listOf("Malasia", "Dinamarca", "Suiza"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.ghana,
                respuestasCorrectas ="Ghana",
                respuestasIncorrectas = listOf("Monaco", "Nueva Zelanda", "Paises Bajos"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.granada,
                respuestasCorrectas ="Granada",
                respuestasIncorrectas = listOf("Montenegro", "España", "Comoras"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.grecia,
                respuestasCorrectas ="Grecia",
                respuestasIncorrectas = listOf("Egipto", "Iran", "Pakistan"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.guatemala,
                respuestasCorrectas ="Guatemala",
                respuestasIncorrectas = listOf("Honduras", "Mexico", "Republica Dominicana"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.guinea,
                respuestasCorrectas ="Guinea",
                respuestasIncorrectas = listOf("Marruecos", "Polonia", "Uganda"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.guinea_bissau,
                respuestasCorrectas ="Guinea Bissau",
                respuestasIncorrectas = listOf("Guinea", "Croacia", "Fiyi"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.guinea_ecuatorial,
                respuestasCorrectas ="Guinea Ecuatorial",
                respuestasIncorrectas = listOf("Guinea Bissau", "Uganda", "Georgia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.guyana,
                respuestasCorrectas ="Guyana",
                respuestasIncorrectas = listOf("Haiti", "Estonia", "Vanuatu"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.haiti,
                respuestasCorrectas ="Haiti",
                respuestasIncorrectas = listOf("Somalia", "Jamaica", "Bulgaria"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.honduras,
                respuestasCorrectas ="Honduras",
                respuestasIncorrectas = listOf("Samoa", "Mongolia", "Comoras"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.hungria,
                respuestasCorrectas ="Hungria",
                respuestasIncorrectas = listOf("Polonia", "Tanzania", "Camboya"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.india,
                respuestasCorrectas ="India",
                respuestasIncorrectas = listOf("Iran", "Pakistan", "Haiti"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.indonesia,
                respuestasCorrectas ="Indonesia",
                respuestasIncorrectas = listOf("Eritrea", "Butan", "Zambia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.irak,
                respuestasCorrectas ="Irak",
                respuestasIncorrectas = listOf("Iran", "Guinea", "Yemen"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.iran,
                respuestasCorrectas ="Iran",
                respuestasIncorrectas = listOf("Italia", "Kazajistan", "Sudafrica"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.irlanda,
                respuestasCorrectas ="Irlanda",
                respuestasIncorrectas = listOf("Trinidad y Tobago", "Lituania", "Belice"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.islandia,
                respuestasCorrectas ="Islandia",
                respuestasIncorrectas = listOf("Reino Unido", "Georgia", "Australia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.islas_marshall,
                respuestasCorrectas ="Islas Marshall",
                respuestasIncorrectas = listOf("Ghana", "Arabia Saudita", "Sudan del Sur"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.islas_salomon,
                respuestasCorrectas ="Islas Salomon",
                respuestasIncorrectas = listOf("San Marino", "Tailandia", "Sudan"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.israel,
                respuestasCorrectas ="Israel",
                respuestasIncorrectas = listOf("Rumania", "Nepal", "Turquia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.italia,
                respuestasCorrectas ="Italia",
                respuestasIncorrectas = listOf("Francia", "Yibuti", "Senegal"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.jamaica,
                respuestasCorrectas ="Jamaica",
                respuestasIncorrectas = listOf("Haiti", "Mexico", "Republica Checa"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.japon,
                respuestasCorrectas ="Japon",
                respuestasIncorrectas = listOf("China", "Pakistan", "Serbia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.jordania,
                respuestasCorrectas ="Jordania",
                respuestasIncorrectas = listOf("Irlanda", "Sierra Leona", "Mali"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.kazajistan,
                respuestasCorrectas ="Kazajistan",
                respuestasIncorrectas = listOf("India", "Noruega", "Irak"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.kenia,
                respuestasCorrectas ="Kenia",
                respuestasIncorrectas = listOf("Laos", "Malasia", "Estonia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.kirguistan,
                respuestasCorrectas ="Kirguistan",
                respuestasIncorrectas = listOf("Portugal", "Angola", "Tunez"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.kiribati,
                respuestasCorrectas ="Kiribati",
                respuestasIncorrectas = listOf("Tuvalu", "Singapur", "Nicaragua"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.kuwait,
                respuestasCorrectas ="Kuwait",
                respuestasIncorrectas = listOf("Republica del Congo", "Nueva Zelanda", "Suecia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.laos,
                respuestasCorrectas ="Laos",
                respuestasIncorrectas = listOf("Grecia", "Dinamarca", "Malasia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.lesoto,
                respuestasCorrectas ="Lesoto",
                respuestasIncorrectas = listOf("Ecuador", "Burkina Faso", "Tailandia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.letonia,
                respuestasCorrectas ="Letonia",
                respuestasIncorrectas = listOf("Guyana", "Camerun", "Serbia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.libano,
                respuestasCorrectas ="Libano",
                respuestasIncorrectas = listOf("Jordania", "Pakistan", "Montenegro"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.liberia,
                respuestasCorrectas ="Liberia",
                respuestasIncorrectas = listOf("Belice", "Cuba", "Siria"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.libia,
                respuestasCorrectas ="Libia",
                respuestasIncorrectas = listOf("Somalia", "Zambia", "Guyana"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.liechtenstein,
                respuestasCorrectas ="Liechtenstein",
                respuestasIncorrectas = listOf("Gabon", "Albania", "Palaos"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.lituania,
                respuestasCorrectas ="Lituania",
                respuestasIncorrectas = listOf("Mozambique", "Kiribati", "Jordania"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.luxemburgo,
                respuestasCorrectas ="Luxemburgo",
                respuestasIncorrectas = listOf("Tonga", "Sudafrica", "Irlanda"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.macedonia_del_norte,
                respuestasCorrectas ="Macedonia del Norte",
                respuestasIncorrectas = listOf("Iran", "Pakistan", "Luxemburgo"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.madagascar,
                respuestasCorrectas ="Madagascar",
                respuestasIncorrectas = listOf("Islas Salomon", "Camboya", "Benin"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.malasia,
                respuestasCorrectas ="Malasia",
                respuestasIncorrectas = listOf("Liberia", "Jamaica", "Granada"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.malaui,
                respuestasCorrectas ="Malaui",
                respuestasIncorrectas = listOf("Kenia", "Indonesia", "Togo"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.maldivas,
                respuestasCorrectas ="Maldivas",
                respuestasIncorrectas = listOf("Israel", "Portugal", "Siria"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.mali,
                respuestasCorrectas ="Mali",
                respuestasIncorrectas = listOf("Mauricio", "Letonia", "Fiyi"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.malta,
                respuestasCorrectas ="Malta",
                respuestasIncorrectas = listOf("Niger", "Panama", "Uganda"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.marruecos,
                respuestasCorrectas ="Marruecos",
                respuestasIncorrectas = listOf("Afganistan", "Turquia", "India"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.mauricio,
                respuestasCorrectas ="Mauricio",
                respuestasIncorrectas = listOf("Luxemburgo", "Jordania", "Guinea"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.mauritania,
                respuestasCorrectas ="Mauritania",
                respuestasIncorrectas = listOf("Hungria", "Malasia", "Angola"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.mexico,
                respuestasCorrectas ="Mexico",
                respuestasIncorrectas = listOf("Namibia", "Colombia", "Canada"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.micronesia,
                respuestasCorrectas ="Micronesia",
                respuestasIncorrectas = listOf("Nepal", "Maldivas", "Kuwait"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.moldavia,
                respuestasCorrectas ="Moldavia",
                respuestasIncorrectas = listOf("Oman", "Palaos", "Namibia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.monaco,
                respuestasCorrectas ="Monaco",
                respuestasIncorrectas = listOf("Libia", "Iran", "Kazajistan"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.mongolia,
                respuestasCorrectas ="Mongolia",
                respuestasIncorrectas = listOf("Noruega", "Albania", "Barbados"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.montenegro,
                respuestasCorrectas ="Montenegro",
                respuestasIncorrectas = listOf("Alemania", "Catar", "Eslovenia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.mozambique,
                respuestasCorrectas ="Mozambique",
                respuestasIncorrectas = listOf("Senegal", "Turquia", "Rumania"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.namibia,
                respuestasCorrectas ="Namibia",
                respuestasIncorrectas = listOf("Malaui", "Italia", "Ghana"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.nauru,
                respuestasCorrectas ="Nauru",
                respuestasIncorrectas = listOf("Lesoto", "Guinea Bissau", "Cuba"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.nepal,
                respuestasCorrectas ="Nepal",
                respuestasIncorrectas = listOf("Pakistan", "Micronesia", "Rumania"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.nicaragua,
                respuestasCorrectas ="Nicaragua",
                respuestasIncorrectas = listOf("Libano", "Albania", "Uzbekistan"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.niger,
                respuestasCorrectas ="Niger",
                respuestasIncorrectas = listOf("Montenegro", "Islandia", "Filipinas"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.nigeria,
                respuestasCorrectas ="Nigeria",
                respuestasIncorrectas = listOf("Noruega", "Tuvalu", "Somalia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.noruega,
                respuestasCorrectas ="Noruega",
                respuestasIncorrectas = listOf("España", "Turquia", "Rumania"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.nueva_zelanda,
                respuestasCorrectas ="Nueva Zelanda",
                respuestasIncorrectas = listOf("Republica Dominicana", "Santa Lucia", "Sri Lanka"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.oman,
                respuestasCorrectas ="Oman",
                respuestasIncorrectas = listOf("Senegal", "Haiti", "Mozambique"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.paises_bajos,
                respuestasCorrectas ="Paises Bajos",
                respuestasIncorrectas = listOf("Francia", "Italia", "Nepal"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.pakistan,
                respuestasCorrectas ="Pakistan",
                respuestasIncorrectas = listOf("Argelia", "Turquia", "Ucrania"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.palaos,
                respuestasCorrectas ="Palaos",
                respuestasIncorrectas = listOf("Timor Oriental", "Singapur", "Moldavia"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.panama,
                respuestasCorrectas ="Panama",
                respuestasIncorrectas = listOf("Namibia", "Liechtenstein", "Chipre"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.papua_nueva_guinea,
                respuestasCorrectas ="Papua Nueva Guinea",
                respuestasIncorrectas = listOf("Argelia", "Nueva Zelanda", "Trinidad y Tobago"),
                caracteristica = "Heroe"
            ),
            Personaje(
                imagenId = R.drawable.paraguay,
                respuestasCorrectas ="Paraguay",
                respuestasIncorrectas = listOf("Panama", "Uruguay", "Chile"),
                caracteristica = "Heroe"
            ),


        )
    }
}
