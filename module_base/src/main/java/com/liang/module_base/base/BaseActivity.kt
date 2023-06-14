package com.liang.module_base.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_base.extension.getNightMode
import com.liang.module_base.extension.getResString
import com.liang.module_base.extension.setNightMode
import com.liang.module_base.utils.ActivityUtil
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.StatusBarUtil
import com.liang.module_base.utils.ToastUtil
import com.liang.module_base.widget.LoadingDialog
import com.google.gson.Gson
import com.liang.module_base.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Time: 2023/4/20/0020 on 10:50
 * @User: Jerry
 * @Description: 使用Hint或者Koin或者扩展类初始化ViewModel，因此BaseActivity中只传入了DataBinding
 */
//abstract class BaseActivity<VM : BaseViewModel, T : ViewDataBinding> : AppCompatActivity() {
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    companion object {
        val TAG: String = BaseActivity::class.java.simpleName
        const val TAG_MSG: String = "actionStart"
    }

    private val actionStart: String = javaClass.simpleName

    lateinit var mBinding: T

    private lateinit var mLoadingDialog: LoadingDialog

//    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still)

        LogUtils.d(tag = TAG_MSG, msg = actionStart)
        LogUtils.d(actionStart, "onChangeLanguage:  init ${LanguageUtilKt.isChinese}")
        mLoadingDialog = LoadingDialog(this)
        ActivityUtil.addActivity(this)
        StatusBarUtil.setTransparentForWindow(this)
        // 让模式深色模式，也就是状态栏字体变黑
        if (getNightMode()) {
            StatusBarUtil.setLightMode(this)
        } else {
            StatusBarUtil.setDarkMode(this)
        }

        StatusBarUtil.setSystemBarMirror(this, true)

//        initViewModel()
        initDataBinding()

        initView(savedInstanceState)
        initData()
        initListener()
        startObserver()
    }

    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(newBase)
        val changeLang = LanguageUtilKt.changeLang(newBase)
        super.attachBaseContext(changeLang)
    }

    private fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        // 需绑定lifecycleOwner到Activity,xml绑定的数据才会随着liveData数据源的改变而改变
        mBinding.lifecycleOwner = this
    }

//    /** ViewModel初始化 */
//    @Suppress("UNCHECKED_CAST")
//    private fun initViewModel() {
//        // 这里利用反射获取泛型中第一个参数ViewModel
//        val type: Class<VM> =
//            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
//        mViewModel = ViewModelProvider(this)[type]
//    }

    abstract fun getLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    open fun startObserver() {
        // 全局服务器请求错误监听
        BaseApp.globalViewModel.apply {
            exception.observe(this@BaseActivity) {
                requestError(it.message)
                LogUtils.e(msg = "======> 网络请求错误：exception ======> $it")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        this@BaseActivity,
                        getString(R.string.request_time_out)
                    )

                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        this@BaseActivity,
                        getString(R.string.network_error)
                    )

                    else -> ToastUtil.showShort(
                        this@BaseActivity, it.message ?: getString(R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(this@BaseActivity) {
                requestError(it?.errorMsg)
                LogUtils.e(msg = "======> 网络请求错误：errorResponse ======> ${Gson().toJson(it)}")
                it?.errorMsg?.run {
                    ToastUtil.showShort(this@BaseActivity, this)
                }
            }
        }
    }

    /**
     * 提供一个请求错误的方法,用于像关闭加载框,显示错误布局之类的
     */
    open fun requestError(msg: String?) {
//        hideLoading()
        dismissLoading()
    }

    open fun initData() {

    }

    open fun initListener() {

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtil.removeActivity(this)
        mBinding.unbind()
    }

    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(this, true)
    }

    /**
     * dismiss loading dialog
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }

    fun toast(message: String) {
        ToastUtil.showShort(this, message)
    }

    fun getClickEmptyDataView(parent: RecyclerView): View {
        val notDataView: View = layoutInflater.inflate(R.layout.empty_view, parent, false)
        notDataView.setOnClickListener { onClickRetry() }
        return notDataView
    }

    fun getClickErrorView(parent: RecyclerView): View {
        val errorView: View = layoutInflater.inflate(R.layout.error_view, parent, false)
        errorView.setOnClickListener { onClickRetry() }
        return errorView
    }

    fun getMsgEmptyDataView(
        parent: RecyclerView,
        msg: String? = R.string.base_no_data.getResString()
    ): View {
        val notDataView: View = layoutInflater.inflate(R.layout.empty_view, parent, false)
        notDataView.findViewById<TextView>(R.id.tv_nodata).text = msg
        return notDataView
    }

    fun getMsgErrorView(
        parent: RecyclerView,
        msg: String? = R.string.base_net_error.getResString()
    ): View {
        val errorView: View = layoutInflater.inflate(R.layout.error_view, parent, false)
        errorView.findViewById<TextView>(R.id.tv_error_msg).text = msg
        return errorView
    }

    open fun onClickRetry() {

    }

    /**
     * 切换暗黑模式
     */
    fun changeDayNightMode() {
        if (getNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setNightMode(false)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setNightMode(true)
        }
    }

    /**
     * 中英文模式切换
     * 切换中英文环境，activity会重启
     */
    open fun changeLanguage(isChineseMode: Boolean) {
        LogUtils.d(actionStart, "onChangeLanguage BaseActivity:  $isChineseMode")
        LanguageUtilKt.setChineseMode(this@BaseActivity, isChineseMode)
//        startTargetActivity<NightModeChangeMaskActivity>()
//        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still)
        recreate()
    }

    open fun isChinese(): Boolean {
        return LanguageUtilKt.isChineseVersion
    }

    open fun getStr(id: Int): String? {
        return if (isChinese()) getChineseStr(id) else getEnglishStr(id)
    }

    open fun getEnglishStr(id: Int): String? {
        return LanguageUtilKt.getStringToEnglish(this, id)
    }

    open fun getChineseStr(id: Int): String? {
        return LanguageUtilKt.getStringToChinese(this, id)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_still, R.anim.scale_exit)
    }
}