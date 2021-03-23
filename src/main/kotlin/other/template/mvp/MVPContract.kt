package other.template.mvp

fun generateMVPContract(
    packageName: String,
    mvpName: String,
    describe: String,
    needRequestMap: Boolean,
    userObjectAsResponse: Boolean
): String {

    var request = ""
    var requestWithDivider = ""

    if (needRequestMap) {
        request = "request: Map<String, String>"
        requestWithDivider = "request: Map<String, String>, "
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


return """package ${packageName}.mvp.contract;

import com.moa.baselib.base.net.mvp.ValueCallback;
$import

/**
 * ${describe}Contract
 *
 * @author wangjian
 * Created on 2020/9/30 17:12
 */
interface ${mvpName}Contract {
    interface I${mvpName}Model {
        fun send${mvpName}Request(${requestWithDivider}callback: ValueCallback<${response}?>?)
    }

    interface I${mvpName}Presenter {
        fun send${mvpName}Request(${request})
    }

    interface I${mvpName}View {
        fun on${mvpName}Success(${resultResponse})
        fun onFail(code: Int, msg: String?)
    }
}
"""
}
