package com.liang.module_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_base.extension.getResString
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_base.widget.LoadingDialog
import com.liang.module_base.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    companion object {
        val TAG: String = BaseFragment::class.java.simpleName
        const val TAG_MSG: String = "actionStart"
    }

    private val actionStart: String = javaClass.simpleName

    lateinit var mBinding: T

    private lateinit var mLoadingDialog: LoadingDialog

    private var isFirstLoad: Boolean = true

    abstract fun getLayoutId(): Int

    abstract fun initView(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.d(tag = TAG_MSG, msg = actionStart)
        LogUtils.d(actionStart, "onChangeLanguage:  ${LanguageUtilKt.isChinese}")
        mLoadingDialog = LoadingDialog(view.context)
        initView(view)
        initData()
        startObserver()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            lazyLoad()
        }
    }

    open fun lazyLoad() {}

    open fun startObserver() {
        BaseApp.globalViewModel.apply {
            exception.observe(viewLifecycleOwner) {
                requestError(it.message)
                LogUtils.e(msg = "======> 网络请求错误：exception ======> ${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        requireContext(),
                        getString(R.string.request_time_out)
                    )
                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        requireContext(),
                        getString(R.string.network_error)
                    )
                    else -> ToastUtil.showShort(
                        requireContext(), it.message ?: getString(R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(viewLifecycleOwner) {
                requestError(it?.errorMsg)
                LogUtils.e(msg = "======> 网络请求错误：errorResponse ======> ${it?.errorMsg}")
                it?.errorMsg?.run {
                    ToastUtil.showShort(requireContext(), this)
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

    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(requireContext(), true)
    }

    /**
     * dismiss loading dialog
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }


    fun toast(message: String) {
        ToastUtil.showShort(requireContext(), message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
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

    open fun isChinese(): Boolean {
        return LanguageUtilKt.isChineseVersion
    }
}