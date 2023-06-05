package com.liang.newbaseproject.bean

import com.liang.newbaseproject.splash.TypewriterBean

object MockTypewriterBeanList {

    //TypewriterBean
    private val bean1 = TypewriterBean("绿蚁新焙酒，红泥小火炉\n晚来天欲雪，能饮一杯无？")
    private val bean2 =
        TypewriterBean(
            "春花秋月何时了？\n往事知多少。\n小楼昨夜又东风，故国不堪回首月明中。\n雕栏玉砌应犹在，只是朱颜改。\n问君能有几多愁？\n恰似一江春水向东流。"
        )
    private val bean3 =
        TypewriterBean("水国蒹葭夜有霜，\n月寒山色共苍苍。\n谁言千里自今夕，\n离梦杳如关塞长。")
    private val bean4 =
        TypewriterBean("太阳靠近赤道，\n我靠近你。\n我们终会上岸，无论去到哪里，\n都是阳光万里，鲜花灿烂。")
    private val bean5 =
        TypewriterBean("要把所有的夜归还给星河，\n把所有的春光归还给疏疏篱落，\n把所有的慵慵沉迷与不前，\n归还给过去的我。\n明日之我，\n胸中有丘壑，\n立马振山河。")
    private val bean6 = TypewriterBean("希望你眼眸有星辰，\n心中有山海。\n从此以梦为马，不负韶华。")
    private val bean7 = TypewriterBean("你与晚霞一样温暖，\n你与星光一样浪漫。")
    private val bean8 = TypewriterBean("我知你遥远如月亮，\n但又好贴近我心脏。")
    private val bean9 =
        TypewriterBean("面对大河我无限惭愧\n我年华虚度，空有一身疲倦\n和所有以梦为马的诗人一样\n岁月易逝,一滴不剩")
    private val bean10 =
        TypewriterBean("所爱隔山海，\n山海皆可平 ，\n海有舟可渡，\n山有路可行 ，\n所爱隔人心，\n人心不可移， \n山海亦可平，\n最难是君心")
    private val bean11 =
        TypewriterBean("我将于茫茫人海中，\n访我唯一灵魂之伴侣；\n得之，我幸；\n不得，我命。")
    private val bean12 =
        TypewriterBean(
            "愿此间山有木兮卿有意，\n" +
                    "\n" +
                    "\n" +
                    "昨夜星辰恰似你，\n" +
                    "\n" +
                    "\n" +
                    "身无双翼，却心有一点灵犀，\n" +
                    "\n" +
                    "\n" +
                    "愿世间春秋与天地，眼中唯有一个你，\n" +
                    "\n" +
                    "\n" +
                    "苦乐悲喜，得失中尽致淋漓，\n" +
                    "\n" +
                    "\n" +
                    "你我情意当如此尽致淋漓。"
        )
    private val bean13 =
        TypewriterBean(
            "初见是惊鸿一瞥，南柯一梦是你。\n" +
                    "\n" +
                    "\n" +
                    "等待是山重水复，怦然心动是你。\n" +
                    "\n" +
                    "相遇是柳暗花明，小心翼翼是你。\n" +
                    "\n" +
                    "\n" +
                    "重逢是始料未及，别来无恙是你。"
        )


    private var typewriterBeans: MutableList<TypewriterBean> = mutableListOf()

    fun initTypewriterBeans(): MutableList<TypewriterBean> {
        typewriterBeans.clear()
        typewriterBeans.run {
            add(bean1)
            add(bean2)
            add(bean3)
            add(bean4)
            add(bean5)
            add(bean6)
            add(bean7)
            add(bean8)
            add(bean9)
            add(bean10)
            add(bean11)
            add(bean12)
            add(bean13)
        }
        return typewriterBeans
    }

}