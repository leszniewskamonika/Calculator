package com.example.calculator.calculator

import bsh.Interpreter
import java.lang.Exception

object CalculatorOutputPresenter {

    //Current attached view
    private var mView: CalculatorOutputInterfaceView? = null

    //Current equation
    private var mCurrentEquation: String = ""

    //Current outcome
    private var mCurrentOutcome: String = ""

    //Interpreter
    private val mInterpreter = Interpreter()

    fun attach(view: CalculatorOutputInterfaceView) {
        mView = view
    }

    fun detach() {
        mView = null
    }

    fun add(item: String) {
        mCurrentEquation = mCurrentEquation.plus(item)
        updateEquation()
        calculatorOutcome()
        updateOutcome()
    }

    fun remove() {
        if (mCurrentEquation.length > 1) {
            mCurrentEquation = mCurrentEquation.substring(0, mCurrentEquation.length - 1)
        } else {
            mCurrentEquation = ""
        }

        updateEquation()
        calculatorOutcome()
        updateOutcome()
    }

    fun solve() {
        if (mCurrentOutcome.isEmpty()) {
            mCurrentEquation = mCurrentOutcome
            mCurrentOutcome = ""
        }
        updateEquation()
        updateOutcome()
    }

    fun clear() {
        mCurrentEquation = ""
        mCurrentOutcome = ""

        updateEquation()
        updateOutcome()
    }

    private fun calculatorOutcome() {
        if (mCurrentEquation.isNotEmpty()) {
            try {
                mInterpreter.eval("result = $mCurrentEquation")
                val result = mInterpreter.get("result")

                if (result != null && result is Int) {
                    mCurrentOutcome = result.toString()
                }
            } catch (e: Exception) {
                mCurrentOutcome = ""
            }

        } else {
            mCurrentOutcome = ""
        }

    }

    private fun updateEquation() {
        mView?.setEquation(mCurrentEquation)
    }

    private fun updateOutcome() {
        mView?.setOutcome(mCurrentOutcome)
    }
}