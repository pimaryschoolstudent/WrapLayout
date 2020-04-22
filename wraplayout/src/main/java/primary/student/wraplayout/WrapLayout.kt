package primary.student.wraplayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup



/**
 * @author HuangJiaHeng
 * @date 2020/4/14.
 */
class WrapLayout(context: Context,attributes: AttributeSet?=null) :ViewGroup(context,attributes){

    companion object{
        const val GRAVITY_CENTER = 1001
        const val GRAVITY_START = 1002
        const val GRAVITY_END = 1003
    }

    private var gravity:Int

    init {
        val ta = context.obtainStyledAttributes(attributes, R.styleable.WrapLayout)
        gravity = ta.getInt(R.styleable.WrapLayout_gravity,1002)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        var sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED){
            sizeHeight = 0
        }

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED){
            sizeWidth = getChildViewWidthMax(widthMeasureSpec,heightMeasureSpec)
        }

        var isMaxWidth = false
        var childAllHeight = 0
        var childHeightMax = 0
        var childAllWidth = 0

        for (i in 0 until childCount){

            val childView = getChildAt(i)
            var childWidth:Int
            var childHeight:Int

            measureChild(childView,widthMeasureSpec,heightMeasureSpec)

            childWidth = getChildViewWidthSize(childView)
            childHeight = getChildViewHeightSize(childView)
            childAllWidth += childWidth

            if (childAllWidth > (sizeWidth-paddingLeft-paddingRight)){

                isMaxWidth = true
                childAllHeight +=childHeightMax
                childHeightMax = 0
                childAllWidth = childWidth
            }

            if (childHeightMax < childHeight){
                childHeightMax = childHeight
            }
        }


        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED ){
            sizeHeight = if (isMaxWidth){
                childAllHeight + paddingTop +paddingBottom + childHeightMax
            }else{
                childHeightMax+ paddingTop + paddingBottom
            }
        }

        setMeasuredDimension(sizeWidth,sizeHeight)

    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var sizeWidth = measuredWidth - paddingLeft - paddingRight

        when (gravity){
            GRAVITY_START -> gravityStartLayout(sizeWidth)
            GRAVITY_END -> gravityEndLayout(sizeWidth)
            GRAVITY_CENTER -> gravityCenterLayout(sizeWidth)
        }


    }

    private fun gravityCenterLayout(sizeWidth:Int){

        var childAllWidth = 0
        var childAllHeight = 0
        var childHeightMax = 0
        var startIndex = 0
        for (i in 0 until childCount){

            var childView = getChildAt(i)

            childAllWidth += getChildViewWidthSize(childView)

            if (childAllWidth >sizeWidth){
                startIndex = i
                childAllHeight += childHeightMax
                childHeightMax = 0
                childAllWidth = getChildViewWidthSize(childView)
            }

            var startLeft = (sizeWidth/2) - (childAllWidth/2)
            for (j in startIndex..i){

                var childView = getChildAt(j)
                var clp = childView.layoutParams as MarginLayoutParams
                var childTop = childAllHeight +clp.topMargin
                var childLeft =  startLeft+clp.leftMargin
                var childRight = childLeft+childView.measuredWidth
                var childBottom = childTop+childView.measuredHeight

                childView.layout(childLeft,childTop,childRight,childBottom)

                startLeft += getChildViewWidthSize(childView)
            }

            if (childHeightMax < getChildViewHeightSize(childView)){
                childHeightMax = getChildViewHeightSize(childView)
            }

        }
    }

    private fun gravityEndLayout(sizeWidth:Int){

        var childAllWidth =0
        var childAllHeight = 0
        var childHeightMax = 0
        var startRight:Int
        var startTop:Int
        for (i in 0 until childCount){

            var childView = getChildAt(i)
            var clp = childView.layoutParams as MarginLayoutParams

            startRight = sizeWidth - (paddingRight + clp.rightMargin + childAllWidth)
            startTop = paddingTop + clp.topMargin + childAllHeight

            childAllWidth += getChildViewWidthSize(childView)

            if (childAllWidth > sizeWidth){
                childAllHeight += childHeightMax
                childAllWidth = 0
                childHeightMax = 0
                startRight =   sizeWidth - (paddingRight + clp.rightMargin + childAllWidth)
                childAllWidth += getChildViewWidthSize(childView)
                startTop = paddingTop + clp.topMargin + childAllHeight
            }

            if (childHeightMax < getChildViewHeightSize(childView)){
                childHeightMax = getChildViewHeightSize(childView)
            }


            var childRight = startRight
            var childLeft = startRight-childView.measuredWidth
            var childTop = startTop
            var childBottom = startTop+childView.measuredHeight

            childView.layout(childLeft,childTop,childRight,childBottom)

        }
    }

    private fun gravityStartLayout(sizeWidth:Int){

        var childAllWidth =0
        var childAllHeight = 0
        var childHeightMax = 0
        var startLeft:Int
        var startTop:Int
        for (i in 0 until childCount){

            var childView = getChildAt(i)
            var clp = childView.layoutParams as MarginLayoutParams

            startLeft = paddingLeft + clp.leftMargin + childAllWidth
            startTop = paddingTop + clp.topMargin + childAllHeight

            childAllWidth += getChildViewWidthSize(childView)

            if (childAllWidth > sizeWidth){
                childAllHeight += childHeightMax
                childAllWidth = 0
                childHeightMax = 0
                startLeft =  paddingLeft + clp.leftMargin
                childAllWidth += getChildViewWidthSize(childView)
                startTop = paddingTop + clp.topMargin + childAllHeight
            }

            if (childHeightMax < getChildViewHeightSize(childView)){
                childHeightMax = getChildViewHeightSize(childView)
            }


            var childLeft = startLeft
            var childRight = startLeft+childView.measuredWidth
            var childTop = startTop
            var childBottom = startTop+childView.measuredHeight

            childView.layout(childLeft,childTop,childRight,childBottom)

        }
    }

    private fun getChildViewWidthSize(childView: View?):Int{
        var clp = childView?.layoutParams as MarginLayoutParams
        return clp.leftMargin+childView.measuredWidth+clp.rightMargin
    }

    private fun getChildViewHeightSize(childView: View?):Int{
        var clp = childView?.layoutParams as MarginLayoutParams
        return clp.topMargin+childView.measuredHeight+clp.bottomMargin
    }

    private fun getChildViewWidthMax(widthMeasureSpec: Int,heightMeasureSpec: Int):Int{

        var childWidthMax = 0

        for (i in 0 until childCount){

            var childView = getChildAt(i)

            measureChild(childView,widthMeasureSpec,heightMeasureSpec)

            var width = getChildViewWidthSize(childView)

            if (childWidthMax < width){
                childWidthMax = width
            }

        }
        return childWidthMax
    }


    fun setGravity(gravity: Int){
        this.gravity = gravity
        this.requestLayout()
    }

    fun getGravity():Int{
        return this.gravity
    }
}