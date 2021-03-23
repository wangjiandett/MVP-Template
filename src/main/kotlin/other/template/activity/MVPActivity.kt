package other.template.activity

fun generateMVPActivity(
    packageName: String,
    mvpName: String,
    layoutName: String,
    describe: String,
    needRequestMap: Boolean,
    userObjectAsResponse: Boolean
): String {

    var request = ""

    request = if (needRequestMap) {
        "val request = hashMapOf<String, String>()\nm${mvpName}Presenter?.send${mvpName}Request(request)"
    } else {
        "m${mvpName}Presenter?.send${mvpName}Request()"
    }

    var import = ""
    var resultResponse = ""

    if (!userObjectAsResponse) {
        import = "import ${packageName}.mvp.bean.${mvpName}Response"
        resultResponse = "response: ${mvpName}Response?"

    } else {
        import = ""
        resultResponse = "response: Any?"
    }

    return """package ${packageName}.mvp.view;
        
import com.moa.baselib.base.ui.BaseActivity
import ${packageName}.R
$import
import ${packageName}.mvp.contract.${mvpName}Contract
import ${packageName}.mvp.model.${mvpName}Model
import ${packageName}.mvp.presenter.${mvpName}Presenter

/**
 * ${describe}Activity
 *
 * @author wangjian
 * Created on 2020/12/23 9:54
 */
class ${mvpName}Activity : BaseActivity(), ${mvpName}Contract.I${mvpName}View {

	private var m${mvpName}Presenter: ${mvpName}Presenter? = null

    override fun getLayoutId(): Int {
		return R.layout.${layoutName}
    }

    override fun initView() {
	
	}
	
    override fun initData() {
		m${mvpName}Presenter = ${mvpName}Presenter(this, ${mvpName}Model())
		loadData()
	}
	
	fun loadData(){
        $request
    }
	
	override fun on${mvpName}Success($resultResponse) {
	
    }

    override fun onFail(code: Int, msg: String?) {
        showToast(msg)
    }
}"""
}