package other.template.mvp

fun generateMVPPresenter(
    packageName: String,
    mvpName: String,
    describe: String,
    needRequestMap: Boolean,
    userObjectAsResponse: Boolean
): String {

    var request = ""
    var requestWithDivider = ""

    if (needRequestMap) {
        request = "request, "
        requestWithDivider = "request: Map<String, String>"
    }

    var import = ""
    var response = ""
    var resultResponse = ""

    if (!userObjectAsResponse) {
        import = "import ${packageName}.mvp.bean.${mvpName}Response"
        response = "${mvpName}Response"
        resultResponse = "response: ${mvpName}Response?"
    } else {
        import = ""
        response = "Any"
        resultResponse = "response: Any?"
    }

return """package ${packageName}.mvp.presenter;

import com.moa.baselib.base.net.mvp.SimpleValueCallback;
$import
import ${packageName}.mvp.contract.${mvpName}Contract;

/**
 * ${describe}Presenter
 *
 * @author wangjian
 * Created on 2020/9/30 17:12
 */
class ${mvpName}Presenter(private val iView: ${mvpName}Contract.I${mvpName}View, private val iModel: ${mvpName}Contract.I${mvpName}Model) : ${mvpName}Contract.I${mvpName}Presenter {
   
   override fun send${mvpName}Request($requestWithDivider) {
        iModel.send${mvpName}Request(${request}object : SimpleValueCallback<${response}?>() {
            override fun onSuccess($resultResponse) {
                iView.on${mvpName}Success(response)
            }

            override fun onFail(code: Int, msg: String) {
                iView.onFail(code, msg)
            }
        })
    }
}
"""
}