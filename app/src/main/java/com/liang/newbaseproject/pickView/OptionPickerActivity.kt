package com.liang.newbaseproject.pickView

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.DateUtilJava
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityTimPickerBinding

class OptionPickerActivity : BaseActivity<ActivityTimPickerBinding>() {

    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_tim_picker
    }

    override fun initView(savedInstanceState: Bundle?) {

        val currentYear = DateUtilJava.getCurrentYear()

        // 日期列表
        val listDay: ArrayList<String> = ArrayList()

        for (i in 1 until 8) {
            val oldDateMontyDay = DateUtilJava.getOldDateMontyDay(i)
            listDay.add(oldDateMontyDay)
        }

        // 时间列表
        val listHour: ArrayList<String> =
            arrayListOf(
                "08:00",
                "09:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00",
                "17:00",
                "18:00",
            )

        // 自定义选项列表
//        val bean1 = PickerBean("丁程鑫", "1")
//        val bean2 = PickerBean("贺峻霖", "2")
//        val bean3 = PickerBean("马嘉祺", "3")
//        val bean4 = PickerBean("姚景元", "4")
//        val bean5 = PickerBean("李天泽", "5")
//        val bean6 = PickerBean("刘耀文", "6")
//        val bean7 = PickerBean("马天宇", "7")
//        val bean8 = PickerBean("丁凯乐", "8")
//        val bean9 = PickerBean("肖战", "9")
//        val bean10 = PickerBean("成毅", "10")

        val bean1 = PickerBean("索纳塔1", "1")
        val bean2 = PickerBean("伊兰特1", "2")
        val bean3 = PickerBean("胜达1", "3")
        val bean4 = PickerBean("途胜1", "4")
        val bean5 = PickerBean("捷尼赛思1", "5")
        val bean6 = PickerBean("索纳塔2", "6")
        val bean7 = PickerBean("伊兰特2", "7")
        val bean8 = PickerBean("胜达2", "8")
        val bean9 = PickerBean("途胜2", "9")
        val bean10 = PickerBean("捷尼赛思2", "10")

        val beanList: List<PickerBean> =
            listOf(bean1, bean2, bean3, bean4, bean5, bean6, bean7, bean8, bean9, bean10)

        val beanNameList: ArrayList<String> = ArrayList()

        for (bean in beanList) {
            beanNameList.add(bean.name)
        }

        mBinding.apply {
            pickerBean.setItems(beanNameList)
            pickerBean.setSelectedItem(beanNameList[0])
            btnBean.setOnClickListener {
                val selectedItem = pickerBean.getSelectedItem()
                ToastUtil.showShort(this@OptionPickerActivity, selectedItem)
                LogUtils.d(tag = "PickerView", msg = "PickerViewBean :$selectedItem")

                for (bean in beanList) {
                    if (bean.name == selectedItem) {
                        LogUtils.d(tag = "PickerView", msg = "PickerViewBean Name :$selectedItem")
                        LogUtils.d(tag = "PickerView", msg = "PickerViewBean ID :${bean.id}")
                    }
                }
            }

            pickerViewDay.setItems(listDay)
            pickerViewDay.setSelectedItem(DateUtilJava.getOldDateMontyDay(1))
            btnDay.setOnClickListener {
                val selectedItem = pickerViewDay.getSelectedItem()
                ToastUtil.showShort(this@OptionPickerActivity, selectedItem)
                LogUtils.d(tag = "PickerView", msg = "PickerViewDay :$selectedItem")
            }

            pickerViewHour.setItems(listHour)
            pickerViewHour.setSelectedItem(listHour[0])
            btnHour.setOnClickListener {
                val selectedItem = pickerViewHour.getSelectedItem() + ":00"
                ToastUtil.showShort(this@OptionPickerActivity, selectedItem)
                LogUtils.d(tag = "PickerView", msg = "PickerViewHour :$selectedItem")
            }

            btnPost.setOnClickListener {
                val selectedItemDay = pickerViewDay.getSelectedItem()
                val selectedItemHour = pickerViewHour.getSelectedItem() + ":00"

                val selectedTimeStamp: String = "$currentYear-$selectedItemDay $selectedItemHour"

                ToastUtil.showShort(
                    this@OptionPickerActivity,
//                    "PickerView :$currentYear-$selectedItemDay $selectedItemHour"
                    "PickerView :$selectedTimeStamp"
                )
                LogUtils.d(
                    tag = "PickerView",
//                    msg = "PickerView :$currentYear-$selectedItemDay $selectedItemHour"
                    msg = "PickerViewSelected :$selectedTimeStamp"
                )

                // 将时间转换为时间戳
//                val dateToStamp = DateUtilJava.dateToStamp(selectedTimeStamp)
                val dateToStamp = DateUtilJava.dateToStamp2(selectedTimeStamp)

                LogUtils.d(
                    tag = "PickerView",
                    msg = "PickerViewStamp :$dateToStamp"
                )

                val niceDate = DateUtilJava.stampToDate(dateToStamp)
                LogUtils.d(
                    tag = "PickerView",
                    msg = "PickerViewNiceDate :$niceDate"
                )
                //currentYear
            }

            btnDialogDay.setOnClickListener {
                OptionPickerDialog
                    .Builder(this@OptionPickerActivity)
                    .setTitle("选择日期")
                    .setHighlightColor(
                        ContextCompat.getColor(
                            this@OptionPickerActivity,
                            com.liang.module_ui.R.color.red
                        )
                    )
                    .setCanceledTouchOutside(true)
                    .setItems(listDay)
                    .setSelectedItem(1)
                    .setOnDataResult {
                        selectedDate = it
                        ToastUtil.showShort(this@OptionPickerActivity, it)
                        LogUtils.d(tag = "PickerViewDialog", msg = "PickerViewDay : $it")
                    }
                    .build()
                    .show()
            }

            btnDialogHour.setOnClickListener {
                OptionPickerDialog
                    .Builder(this@OptionPickerActivity)
                    .setTitle("选择时间")
                    .setHighlightColor(
                        ContextCompat.getColor(
                            this@OptionPickerActivity,
                            com.liang.module_ui.R.color.colorBlue
                        )
                    )
                    .setCanceledTouchOutside(true)
                    .setItems(listHour)
                    .setSelectedItem(0)
                    .setOnDataResult {
                        val selectedItem = "$it:00"
                        selectedTime = selectedItem
                        ToastUtil.showShort(this@OptionPickerActivity, selectedItem)
                        LogUtils.d(tag = "PickerViewDialog", msg = "PickerViewHour : $selectedItem")
                    }
                    .build()
                    .show()

            }

            btnDialogBean.setOnClickListener {
                OptionPickerDialog
                    .Builder(this@OptionPickerActivity)
                    .setTitle("选择你喜欢的车型")
                    .setHighlightColor(
                        ContextCompat.getColor(
                            this@OptionPickerActivity,
                            com.liang.module_ui.R.color.black
                        )
                    )
                    .setCanceledTouchOutside(false)
                    .setItems(beanNameList)
                    .setSelectedItem(8)
                    .setOnDataResult {
                        ToastUtil.showShort(this@OptionPickerActivity, it)
                        LogUtils.d(tag = "PickerViewDialog", msg = "PickerViewBean :$it")

                        for (bean in beanList) {
                            if (bean.name == it) {
                                LogUtils.d(
                                    tag = "PickerViewDialog",
                                    msg = "PickerViewBean Name :${bean.name}"
                                )
                                LogUtils.d(
                                    tag = "PickerViewDialog",
                                    msg = "PickerViewBean ID :${bean.id}"
                                )

                                val pickerBean1 = PickerBean(it, bean.id)
                                LogUtils.d(
                                    tag = "PickerViewDialog",
                                    msg = "PickerViewBean JSON :${GsonUtils.toJson(pickerBean1)}"
                                )
                            }
                        }
                    }
                    .build()
                    .show()
            }

            btnDialogPost.setOnClickListener {
                val selectedTimeStamp = "$currentYear-$selectedDate $selectedTime"

                ToastUtil.showShort(
                    this@OptionPickerActivity,
                    "PickerViewDialog :$selectedTimeStamp"
                )
                LogUtils.d(
                    tag = "PickerView",
                    msg = "PickerViewDialog :$selectedTimeStamp"
                )

                // 将时间转换为时间戳
//                val dateToStamp = DateUtilJava.dateToStamp(selectedTimeStamp)
                val dateToStamp = DateUtilJava.dateToStamp2(selectedTimeStamp)

                LogUtils.d(
                    tag = "PickerViewDialog",
                    msg = "PickerViewStamp :$dateToStamp"
                )

                val niceDate = DateUtilJava.stampToDate(dateToStamp)
                LogUtils.d(
                    tag = "PickerViewDialog",
                    msg = "PickerViewNiceDate :$niceDate"
                )
            }
        }
    }
}