import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return person by id=1"

    request {
        url "/latest?base=cny"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                base: "CNY",
                date: "2017-08-30",
                rates: [
                        AUD: 0.19114,
                        BGN: 0.24896,
                        BRL: 0.48076,
                        CAD: 0.19044,
                        CHF: 0.14539,
                        CZK: 3.3153,
                        DKK: 0.94694,
                        GBP: 0.11742,
                        HKD: 1.1871,
                        HRK: 0.94369,
                        HUF: 38.911,
                        IDR: 2024.5,
                        ILS: 0.5436,
                        INR: 9.7114,
                        JPY: 16.707,
                        KRW: 170.37,
                        MXN: 2.7097,
                        MYR: 0.64776,
                        NOK: 1.1819,
                        NZD: 0.20974,
                        PHP: 7.77,
                        PLN: 0.54224,
                        RON: 0.58447,
                        RUB: 8.8878,
                        SEK: 1.2105,
                        SGD: 0.20579,
                        THB: 5.0404,
                        TRY: 0.52489,
                        USD: 0.15168,
                        ZAR: 1.9802,
                        EUR: 0.12729
                ]
        )
    }
}