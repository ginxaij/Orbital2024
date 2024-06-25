package com.example.wealthwings.model

data class Question (
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

val CPFQuestions = listOf( // Currently got 10 questions
    Question(
        text = "Which of this is not a type of CPF account?",
        options = listOf("Ordinary Account", "Special Account", "Retirement Account", "Investment Account"),
        correctAnswerIndex = 3
    ),
    Question(
        text = "Which of this is a type of CPF account?",
        options = listOf("Unique Account", "Accommodation Account", "Medisave Account", "Investment Account"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "Which account typically has the highest Interest Rate?",
        options = listOf("Ordinary Account", "Accommodation Account", "Unique Account", "Special Account"),
        correctAnswerIndex = 3
    ),
    Question(
        text = "Which account typically has the lowest Interest Rate",
        options = listOf("Ordinary Account", "Retirement Account", "Medisave Account", "Special Account"),
        correctAnswerIndex = 0
    ),
    Question(
        text = "What does CPF stand for?",
        options = listOf("Central Pension Fund", "Central Provident Fund", "Central Property Fund", "Central People Fund"),
        correctAnswerIndex = 1
    ), //
    Question(
        text = "At what age is the CPF Retirement Account (RA) created?",
        options = listOf("55", "60", "62", "65"),
        correctAnswerIndex = 0
    ),
    Question(
        text = "Which CPF account is primarily used for housing expenses?",
        options = listOf("Ordinary Account", "Special Account", "Medisave Account", "Retirement Account"),
        correctAnswerIndex = 0
    ),
    Question(
        text = "What is CPF LIFE?",
        options = listOf("A savings account for education", "A life insurance policy", "An annuity scheme providing lifelong monthly payouts", "A health insurance plan"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "What is the typical interest rate for the CPF Special Account (SA)?",
        options = listOf("2.5%", "1.6%", "4.0%", "5%"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "Which of the following expenses can CPF Ordinary Account (OA) funds NOT be used for?",
        options = listOf("Buying a home", "Paying for a university education", "Investing in stocks and shares", "Buying groceries"),
        correctAnswerIndex = 3
    ),
)

val TVMQuestions = listOf(
    Question(
        text = "Which of the following is an example of an annuity?",
        options = listOf("A single lump-sum payment", "Monthly rent payments", "Annual dividends from stocks", "A one-time inheritance"),
        correctAnswerIndex = 1
    ),
    Question(
        text = "What does the present value formula calculate?",
        options = listOf("The future value of a series of cash flows", "The value of money at a future date", "The current worth of a future sum of money", "The total amount to be paid over a loan period"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "Which factor is NOT considered in the time value of money?",
        options = listOf("Interest rate", "Inflation", "Time period", "Color of money"),
        correctAnswerIndex = 3
    ),
    Question(
        text = "If you invest $1,000 at an annual interest rate of 5% compounded yearly, how much will you have after 3 years?",
        options = listOf("\$1,150.00", "\$1,157.63", "\$1,162.50", "\$1,200.00"),
        correctAnswerIndex = 1
    ),
    Question(
        text = "What is the future value of $500 invested for 4 years at an annual interest rate of 6% compounded annually?",
        options = listOf("\$612.50", "\$631.23", "\$634.64", "\$650.00"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "Which of the following is the correct formula for calculating present value?",
        options = listOf("PV = FV / (1 + r)^n", "PV = FV * (1 + r)^n", "PV = FV * r * n", "PV = FV / r * n"),
        correctAnswerIndex = 0
    ),
    Question(
        text = "If the interest rate decreases, what happens to the present value of future cash flows?",
        options = listOf("It decreases", "It increases", "It remains the same", "It fluctuates randomly"),
        correctAnswerIndex = 1
    ),
    Question(
        text = "Which term describes the process of determining the value of a future amount of money in today's dollars?",
        options = listOf("Amortizing", "Compounding", "Discounting", "Accruing"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "How much would you need to invest today at an interest rate of 8% per year to have $2,000 in 5 years?",
        options = listOf("\$1,344.56", "\$1,361.00", "\$1,380.38", "\$1,400.00"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "What does compounding refer to in the context of time value of money?",
        options = listOf("Paying interest on the initial principal only", "Paying interest on both the initial principal and the accumulated interest", "Calculating interest using simple interest method", "Distributing interest payments at regular intervals"),
        correctAnswerIndex = 1
    )
)

val StockQuestions = listOf(
    Question(
        text = "What does it mean to buy shares in a company?",
        options = listOf("You are lending money to the company", "You own a part of the company", "You are buying the company's products", "You are employed by the company"),
        correctAnswerIndex = 1
    ),
    Question(
        text = "What is a dividend?",
        options = listOf("The increase in the stock price", "A payment made by a company to its shareholders", "The cost of buying a share", "A type of stock"),
        correctAnswerIndex = 1
    ),
    Question(
        text = "What is an IPO?",
        options = listOf("Initial Price Offering", "International Purchase Order", "Initial Public Offering", "Investment Portfolio Option"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "Which of the following is a stock exchange in Singapore?",
        options = listOf("NYSE", "LSE", "SGX", "TSE"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "What is a stock market index?",
        options = listOf("A measurement of the performance of a group of stocks", "The price of a single stock", "The total value of all stocks", "The location of a stock exchange"),
        correctAnswerIndex = 0
    ),
    Question(
        text = "What does 'blue-chip stock' refer to?",
        options = listOf("Stocks in companies that are very risky", "Stocks in small, new companies", "Stocks in large, reputable, and financially sound companies", "Stocks that are very cheap"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "What is the primary function of a stockbroker?",
        options = listOf("To regulate the stock market", "To provide loans for buying stocks", "To facilitate the buying and selling of stocks", "To issue stocks"),
        correctAnswerIndex = 2
    ),
    Question(
        text = "What is a bear market?",
        options = listOf("A market where prices are rising", "A market where prices are falling", "A market with little or no movement", "A market dominated by individual investors"),
        correctAnswerIndex = 1
    ),
    Question(
        text = "What is meant by 'market capitalization'?",
        options = listOf("The total value of a company's outstanding shares", "The amount of money a company has in the bank", "The total sales revenue of a company", "The total profits of a company"),
        correctAnswerIndex = 0
    ),
    Question(
        text = "What does it mean if a stock is 'overvalued'?",
        options = listOf("The stock's price is higher than its intrinsic value", "The stock's price is lower than its intrinsic value", "The stock pays high dividends", "The stock is not traded frequently"),
        correctAnswerIndex = 0
    )
)