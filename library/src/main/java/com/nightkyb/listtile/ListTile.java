package com.nightkyb.listtile;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 轻量级的列表项组件，包含必选的title文本，可选的heading图片，subtitle文本，extra文本以及trailing图片。
 * title、subtitle限制单行，extra允许多行。
 *
 * @author nightkyb created at 2019/7/22 11:53
 */
public class ListTile extends ViewGroup {
    public static final int DEFAULT_ICON_COLOR = 0x8A000000; // md_black_54
    public static final int DEFAULT_TITLE_COLOR = 0xDD000000; // md_black_87
    public static final int DEFAULT_SUBTITLE_COLOR = 0x8A000000; // md_black_54
    public static final int DEFAULT_EXTRA_COLOR = 0x8A000000; // md_black_54

    private ImageView leading;
    private ImageView trailing;
    private TextView title;
    private TextView subtitle;
    private TextView extra;

    private int leadingRightMargin; // 默认16dp
    private int trailingLeftMargin; // 默认0dp
    private int subtitleTopMargin; // 默认4dp
    private int extraLeftMargin; // 默认16dp

    public ListTile(Context context) {
        this(context, null);
    }

    public ListTile(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListTile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setPadding(dp2px(16.0f), dp2px(8.0f), dp2px(16.0f), dp2px(8.0f));

        // 获取自定义属性
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ListTile);

        Drawable leadingDrawable = ta.getDrawable(R.styleable.ListTile_lt_leading);
        Drawable trailingDrawable = ta.getDrawable(R.styleable.ListTile_lt_trailing);
        CharSequence titleText = ta.getText(R.styleable.ListTile_lt_title);
        CharSequence subtitleText = ta.getText(R.styleable.ListTile_lt_subtitle);
        CharSequence extraText = ta.getText(R.styleable.ListTile_lt_extra);

        if (leadingDrawable != null) {
            if (ta.hasValue(R.styleable.ListTile_lt_leading_color)) {
                int leadingColor = ta.getColor(R.styleable.ListTile_lt_leading_color, DEFAULT_ICON_COLOR);
                leadingDrawable = tint(leadingDrawable, leadingColor);
            }

            int leadingSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_leading_size, dp2px(24.0f));
            leadingRightMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_leading_right_margin, dp2px(16.0f));

            leading = new ImageView(getContext());
            leading.setScaleType(ScaleType.CENTER_CROP);
            leading.setImageDrawable(leadingDrawable);
            leading.setLayoutParams(new LayoutParams(leadingSize, leadingSize));

            addView(leading);
        }

        if (trailingDrawable != null) {
            if (ta.hasValue(R.styleable.ListTile_lt_trailing_color)) {
                int trailingColor = ta.getColor(R.styleable.ListTile_lt_trailing_color, DEFAULT_ICON_COLOR);
                trailingDrawable = tint(trailingDrawable, trailingColor);
            }

            int trailingSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_trailing_size, dp2px(24.0f));
            trailingLeftMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_trailing_left_margin, 0);

            trailing = new ImageView(getContext());
            trailing.setScaleType(ScaleType.CENTER_CROP);
            trailing.setImageDrawable(trailingDrawable);
            trailing.setLayoutParams(new LayoutParams(trailingSize, trailingSize));

            addView(trailing);
        }

        if (titleText != null) {
            int titleSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_title_size, sp2px(16.0f));

            title = new TextView(getContext());
            title.setText(titleText);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
            title.setTextColor(DEFAULT_TITLE_COLOR);
            title.setLines(1);
            title.setEllipsize(TextUtils.TruncateAt.END);
            title.setGravity(Gravity.START);

            addView(title);
        } else {
            throw new IllegalArgumentException("Title must be set!");
        }

        if (subtitleText != null) {
            int subtitleSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_subtitle_size, sp2px(14.0f));
            subtitleTopMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_subtitle_top_margin, dp2px(4.0f));

            subtitle = new TextView(getContext());
            subtitle.setText(subtitleText);
            subtitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtitleSize);
            subtitle.setTextColor(DEFAULT_SUBTITLE_COLOR);
            subtitle.setLines(1);
            subtitle.setEllipsize(TextUtils.TruncateAt.END);
            subtitle.setGravity(Gravity.START);

            addView(subtitle);
        }

        if (extraText != null) {
            int extraSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_extra_size, sp2px(14.0f));
            extraLeftMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_extra_left_margin, dp2px(16.0f));

            extra = new TextView(getContext());
            extra.setText(extraText);
            extra.setTextSize(TypedValue.COMPLEX_UNIT_PX, extraSize);
            extra.setTextColor(DEFAULT_EXTRA_COLOR);
            extra.setGravity(Gravity.END);

            addView(extra);
        }

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (leading != null) {
            measureChild(leading, widthMeasureSpec, heightMeasureSpec);
            int leadingMeasuredWidth = leading.getMeasuredWidth();
            int leadingMeasuredHeight = leading.getMeasuredHeight();

            width += leadingMeasuredWidth + leadingRightMargin;
            height = Math.max(height, leadingMeasuredHeight);
        }

        if (trailing != null) {
            measureChild(trailing, widthMeasureSpec, heightMeasureSpec);
            int trailingMeasuredWidth = trailing.getMeasuredWidth();
            int trailingMeasuredHeight = trailing.getMeasuredHeight();

            width += trailingMeasuredWidth + trailingLeftMargin;
            height = Math.max(height, trailingMeasuredHeight);
        }

        measureChild(title, widthMeasureSpec, heightMeasureSpec);
        int titleMeasuredWidth = title.getMeasuredWidth();
        int titleMeasuredHeight = title.getMeasuredHeight();

        int subtitleMeasuredWidth = 0;
        int subtitleMeasuredHeight = 0;
        if (subtitle != null) {
            measureChild(subtitle, widthMeasureSpec, heightMeasureSpec);
            subtitleMeasuredWidth = subtitle.getMeasuredWidth();
            subtitleMeasuredHeight = subtitle.getMeasuredHeight();
        }

        int maxTitleWidth = Math.max(titleMeasuredWidth, subtitleMeasuredWidth);
        width += maxTitleWidth;
        height = Math.max(height, titleMeasuredHeight + subtitleMeasuredHeight + subtitleTopMargin);

        int extraMeasuredWidth = 0;
        if (extra != null) {
            measureChild(extra, widthMeasureSpec, heightMeasureSpec);
            extraMeasuredWidth = extra.getMeasuredWidth();
            int extraMeasuredHeight = extra.getMeasuredHeight();

            width += extraMeasuredWidth + extraLeftMargin;
            height = Math.max(height, extraMeasuredHeight);
        }

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();

        // title/subtitle + extra超过最大可用空间，但是其中之一没有超过最大可用空间
        // TODO: 2019/8/1 优化：title/subtitle、extra都超过最大可用空间时，两者该如何布局
        if (width > widthSize) {
            if (maxTitleWidth > extraMeasuredWidth) {
                // 裁剪title
                int titleActualWidth = maxTitleWidth - (width - widthSize);
                LayoutParams lpTitle = title.getLayoutParams();
                lpTitle.width = titleActualWidth;
                title.setLayoutParams(lpTitle);

                // 裁剪subtitle
                if (subtitle != null) {
                    LayoutParams lpSubtitle = subtitle.getLayoutParams();
                    lpSubtitle.width = titleActualWidth;
                    subtitle.setLayoutParams(lpSubtitle);
                }
            } else {
                // 裁剪extra
                int extraActualWidth = extraMeasuredWidth - (width - widthSize);
                LayoutParams layoutParams = extra.getLayoutParams();
                layoutParams.width = extraActualWidth;
                extra.setLayoutParams(layoutParams);
            }
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();
        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();

        int childLeft = parentLeft;

        if (leading != null) {
            int width = leading.getMeasuredWidth();
            int height = leading.getMeasuredHeight();
            int childTop = parentTop + (parentBottom - parentTop - height) / 2;

            leading.layout(childLeft, childTop, childLeft + width, childTop + height);
            childLeft += width + leadingRightMargin;
        }

        if (subtitle == null) {
            int width = title.getMeasuredWidth();
            int height = title.getMeasuredHeight();
            int childTop = parentTop + (parentBottom - parentTop - height) / 2;

            title.layout(childLeft, childTop, childLeft + width, childTop + height);
            // childLeft += width;
        } else {
            int titleWidth = title.getMeasuredWidth();
            int titleHeight = title.getMeasuredHeight();
            int subtitleWidth = subtitle.getMeasuredWidth();
            int subtitleHeight = subtitle.getMeasuredHeight();
            int childTop = parentTop + (parentBottom - parentTop - titleHeight - subtitleHeight - subtitleTopMargin) / 2;

            title.layout(childLeft, childTop, childLeft + titleWidth, childTop + titleHeight);
            childTop += titleHeight + subtitleTopMargin;
            subtitle.layout(childLeft, childTop, childLeft + subtitleWidth, childTop + subtitleHeight);
            // childLeft += Math.max(titleWidth, subtitleWidth);
        }

        int childRight = parentRight;

        if (trailing != null) {
            int width = trailing.getMeasuredWidth();
            int height = trailing.getMeasuredHeight();
            int childTop = parentTop + (parentBottom - parentTop - height) / 2;

            trailing.layout(childRight - width, childTop, childRight, childTop + height);
            childRight -= trailingLeftMargin + width;
        }

        if (extra != null) {
            int width = extra.getMeasuredWidth();
            int height = extra.getMeasuredHeight();
            int childTop = parentTop + (parentBottom - parentTop - height) / 2;

            extra.layout(childRight - width, childTop, childRight, childTop + height);
            // childRight -= extraLeftMargin + width;
        }
    }

    private int dp2px(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    private int sp2px(float sp) {
        return (int) (sp * getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * 对目标Drawable进行着色
     *
     * @param drawable 目标Drawable
     * @param color    着色Color
     * @return 着色处理后的Drawable
     */
    private Drawable tint(@NonNull Drawable drawable, @ColorInt int color) {
        // 获取此drawable的共享状态实例
        Drawable.ConstantState state = drawable.getConstantState();
        // 对drawable进行重新实例化、包装、可变操作
        Drawable wrappedDrawable = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        // 进行着色
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public ImageView getLeading() {
        return leading;
    }

    public ImageView getTrailing() {
        return trailing;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getSubtitle() {
        return subtitle;
    }

    public TextView getExtra() {
        return extra;
    }

    public void tintLeading(@ColorRes int color) {
        int tint = ContextCompat.getColor(getContext(), color);
        leading.setImageDrawable(tint(leading.getDrawable(), tint));
    }

    public void tintTrailing(@ColorRes int color) {
        int tint = ContextCompat.getColor(getContext(), color);
        trailing.setImageDrawable(tint(trailing.getDrawable(), tint));
    }
}
