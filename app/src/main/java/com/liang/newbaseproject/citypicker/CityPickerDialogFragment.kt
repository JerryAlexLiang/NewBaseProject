package com.liang.newbaseproject.citypicker

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_base.utils.setOnClickListener
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.DialogCityPickerBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class CityPickerDialogFragment : DialogFragment() {

    lateinit var mBinding: DialogCityPickerBinding

    private val viewModel: CityViewModel by activityViewModel()

    companion object {
        fun newInstance(): CityPickerDialogFragment {
            return CityPickerDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setWindowParams()
//        return super.onCreateView(inflater, container, savedInstanceState)

        // 第一次进入默认不选中省市
        viewModel.setProvinceOrCityIsCheck(0)

        mBinding = DialogCityPickerBinding.inflate(layoutInflater)

        initView()

        startObserver()

        initListener()

        return mBinding.root
    }

    private fun dismissDialog() {
        // 初始设置不选中省份
        viewModel.setCheckProvinceInfo(null)
        // 设置选中城市为空
        viewModel.checkedCity = null
        dismiss()
    }

    private fun initListener() {
        mBinding.apply {
            setOnClickListener(rbProvince, rbCity, btnCancel, btnOk) {
                when (this) {
                    // 点中省份按钮，切换到ProvinceFragment
                    rbProvince -> {
                        rbProvince.isChecked = true
                        rbCity.isChecked = false
                        viewPager.currentItem = 0
                    }

                    // 点中城市按钮，切换到CityFragment
                    rbCity -> {
//                        viewModel.checkedProvinceLiveData.observe(this@CityPickerDialogFragment) {
//                            LogUtils.d(tag = "checkedProvince", msg = GsonUtils.toJson(it))
//                            // 如果当前没有选中具体省份，则提示请选择省份
//                            if (it == null) {
//                                rbProvince.isChecked = true
//                                rbCity.isChecked = false
//                                ToastUtil.showShort(requireContext(), "请选择省份")
//                            } else {
//                                rbProvince.isChecked = false
//                                rbCity.isChecked = true
//                                viewPager.currentItem = 1
//                            }
//                        }

                        val checkedCity = viewModel.checkedCity
                        // 如果当前没有选中具体省份，则提示请选择省份
                        if (checkedCity == null) {
                            rbProvince.isChecked = true
                            rbCity.isChecked = false
                            ToastUtil.showShort(requireContext(), "请选择省份")
                        } else {
                            rbProvince.isChecked = false
                            rbCity.isChecked = true
                            viewPager.currentItem = 1
                        }
                    }

                    // 取消按钮
                    btnCancel -> {
                        dismissDialog()
                    }

                    // 提交按钮
                    btnOk -> {
                        val checkedCity = viewModel.checkedCity
                        if (checkedCity == null) {
                            ToastUtil.showFailRectangleToast(
                                    requireContext(),
                                    true,
                                    "请选择城市"
                            )
                        } else {
                            LogUtils.d(tag = "CityTagAdapter3", msg = "选中: ${checkedCity.name}")
                            ToastUtil.showSuccessRectangleToast(
                                    requireActivity(), true,
                                    checkedCity.name
                            )
                            dismissDialog()
                        }
                    }
                }
            }
        }
    }

    private fun startObserver() {
        viewModel.provinceOrCityIsCheck.observe(this) {
            LogUtils.d(tag = "省市选中Flag", msg = "省市选中Flag:$it")

            if (it == 1) {
                // 切换到省份ViewPager-ProvinceFragment
                mBinding.viewPager.currentItem = 0
                // 更新父容器DialogFragment中的UI
                setCheckProvinceUI()
            } else if (it == 2) {
                mBinding.viewPager.currentItem = 1
                // 更新父容器DialogFragment中的UI
                setCheckCityUI()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        // 布局宽度设置为屏幕总宽度90%，剩下10%宽度被均分到两侧作为边距。也可以通过设计稿边距像素密度，反向计算主布局所需比例。

        // 通过WindowManager获取(已被弃用)
//        val dm = DisplayMetrics()
//        activity?.windowManager?.defaultDisplay?.getMetrics(dm)

        val dm = resources.displayMetrics
        dialog?.window?.setLayout(((dm.widthPixels * 0.8).toInt()), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setWindowParams() {
        // 隐藏标题栏, 不加弹窗上方会一个透明的标题栏占着空间
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 必须设置这两个,才能设置宽度
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.decorView.setBackgroundColor(Color.TRANSPARENT)
        // 遮罩层透明度
        dialog!!.window!!.setDimAmount(0f)

        // 设置宽度
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
//        params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        params.gravity = Gravity.CENTER_HORIZONTAL
        params.windowAnimations = R.style.DatePicker_DialogAnimation
        dialog!!.window!!.setAttributes(params)
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        mBinding = DialogCityPickerBinding.inflate(layoutInflater)
//
//        initView()
//
//        val dialog = AlertDialog.Builder(requireContext())
//                .setView(mBinding.root)
//                .create()
//
//        mBinding.btnCancel.setOnClickListener {
//            ToastUtil.showShort(this.requireContext(), "Cancel")
//        }
//
//        mBinding.btnOk.setOnClickListener {
//            ToastUtil.showShort(this.requireContext(), "Ok")
//        }
//
//        return dialog
//    }


    private fun initView() {
        // 设置点击屏幕不消失
        dialog?.setCanceledOnTouchOutside(false)

        setCheckProvinceUI()
        mBinding.rbProvince.isChecked = true
        mBinding.rbCity.isChecked = false
        initViewPager()
    }

    private fun initViewPager() {
        // 禁用预加载
        mBinding.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        val fragmentViewPagerAdapter = FragmentViewPagerAdapter2(this.requireActivity())
        mBinding.viewPager.adapter = fragmentViewPagerAdapter
        // false表示禁止，true表示允许
        mBinding.viewPager.setUserInputEnabled(false)
    }

    private fun setCheckProvinceUI() {
        mBinding.apply {
            rbProvince.isChecked = true
            rbCity.isChecked = false
        }
    }

    private fun setCheckCityUI() {
        mBinding.apply {
            rbProvince.isChecked = false
            rbCity.isChecked = true
        }
    }


}