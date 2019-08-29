package com.nightkyb.listtile;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Objects;

/**
 * 轻量级的列表项组件，包含必选的title文本，可选的heading图片，subtitle文本，extra文本/图片以及trailing图片。
 * title、subtitle限制单行，extra允许多行。
 *
 * @author nightkyb created at 2019/7/22 11:53
 */
public class ListTile extends ViewGroup {
    private static final int DEF_STYLE_RES = R.style.ListTileStyle;
    private static final int EXTRA_TYPE_TEXT = 1;
    private static final int EXTRA_TYPE_IMAGE = 2;

    private ImageView leading;
    private ImageView trailing;
    private TextView title;
    private TextView subtitle;
    private View extra;
    private int extraType;

    @Px
    private int leadingRightMargin;
    @Px
    private int trailingLeftMargin;
    @Px
    private int subtitleTopMargin;
    @Px
    private int extraLeftMargin;

    public ListTile(Context context) {
        this(context, null);
    }

    public ListTile(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.listTileStyle);
    }

    public ListTile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, DEF_STYLE_RES);

        // 获取自定义属性
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ListTile, defStyleAttr, DEF_STYLE_RES);

        if (ta.hasValue(R.styleable.ListTile_lt_leading)) {
            Drawable leadingDrawable = ta.getDrawable(R.styleable.ListTile_lt_leading);

            if (leadingDrawable != null) {
                if (ta.hasValue(R.styleable.ListTile_lt_leading_color)) {
                    int leadingColor = ta.getColor(R.styleable.ListTile_lt_leading_color, 0);
                    leadingDrawable = tint(leadingDrawable, leadingColor);
                }

                int leadingSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_leading_size, 0);
                leadingRightMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_leading_right_margin, 0);

                leading = new ImageView(context);
                leading.setScaleType(ScaleType.CENTER_CROP);
                leading.setImageDrawable(leadingDrawable);
                leading.setLayoutParams(new LayoutParams(leadingSize, leadingSize));

                addView(leading);
            }
        }

        if (ta.hasValue(R.styleable.ListTile_lt_trailing)) {
            Drawable trailingDrawable = ta.getDrawable(R.styleable.ListTile_lt_trailing);

            if (trailingDrawable != null) {
                if (ta.hasValue(R.styleable.ListTile_lt_trailing_color)) {
                    int trailingColor = ta.getColor(R.styleable.ListTile_lt_trailing_color, 0);
                    trailingDrawable = tint(trailingDrawable, trailingColor);
                }

                int trailingSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_trailing_size, 0);
                trailingLeftMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_trailing_left_margin, 0);

                trailing = new ImageView(context);
                trailing.setScaleType(ScaleType.CENTER_CROP);
                trailing.setImageDrawable(trailingDrawable);
                trailing.setLayoutParams(new LayoutParams(trailingSize, trailingSize));

                addView(trailing);
            }
        }

        if (ta.hasValue(R.styleable.ListTile_lt_title)) {
            CharSequence titleText = ta.getText(R.styleable.ListTile_lt_title);

            if (titleText != null) {
                int titleTextAppearance = ta.getResourceId(R.styleable.ListTile_lt_title_textAppearance, 0);
                int titleColor = ta.getColor(R.styleable.ListTile_lt_title_color, 0);

                title = new TextView(context);
                title.setText(titleText);
                title.setTextAppearance(context, titleTextAppearance);
                title.setTextColor(titleColor);
                title.setLines(1);
                title.setEllipsize(TextUtils.TruncateAt.END);
                title.setGravity(Gravity.START);

                addView(title);
            } else {
                throw new IllegalArgumentException("Title must be a String!");
            }
        } else {
            throw new IllegalArgumentException("Title must be set!");
        }

        if (ta.hasValue(R.styleable.ListTile_lt_subtitle)) {
            CharSequence subtitleText = ta.getText(R.styleable.ListTile_lt_subtitle);

            if (subtitleText != null) {
                int subtitleTextAppearance = ta.getResourceId(R.styleable.ListTile_lt_subtitle_textAppearance, 0);
                int subtitleColor = ta.getColor(R.styleable.ListTile_lt_subtitle_color, 0);
                subtitleTopMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_subtitle_top_margin, 0);

                subtitle = new TextView(context);
                subtitle.setText(subtitleText);
                subtitle.setTextAppearance(context, subtitleTextAppearance);
                subtitle.setTextColor(subtitleColor);
                subtitle.setLines(1);
                subtitle.setEllipsize(TextUtils.TruncateAt.END);
                subtitle.setGravity(Gravity.START);

                addView(subtitle);
            }
        }

        if (ta.hasValue(R.styleable.ListTile_lt_extra)) {
            extraType = ta.getInt(R.styleable.ListTile_lt_extra_type, EXTRA_TYPE_TEXT); // 默认text类型

            if (extraType == EXTRA_TYPE_TEXT) {
                CharSequence extraText = ta.getText(R.styleable.ListTile_lt_extra);

                if (extraText != null) {
                    int extraTextAppearance = ta.getResourceId(R.styleable.ListTile_lt_extra_textAppearance, 0);
                    int extraColor = ta.getColor(R.styleable.ListTile_lt_extra_color, 0);
                    extraLeftMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_extra_left_margin, 0);

                    TextView extraTextView = new TextView(context);
                    extraTextView.setText(extraText);
                    extraTextView.setTextAppearance(context, extraTextAppearance);
                    extraTextView.setTextColor(extraColor);
                    extraTextView.setGravity(Gravity.END);

                    extra = extraTextView;

                    addView(extra);
                }
            } else {
                Drawable extraDrawable = ta.getDrawable(R.styleable.ListTile_lt_extra);

                if (extraDrawable != null) {
                    if (ta.hasValue(R.styleable.ListTile_lt_extra_color)) {
                        int extraColor = ta.getColor(R.styleable.ListTile_lt_extra_color, 0);
                        extraDrawable = tint(extraDrawable, extraColor);
                    }

                    int extraSize = ta.getDimensionPixelSize(R.styleable.ListTile_lt_extra_size, 0);
                    extraLeftMargin = ta.getDimensionPixelSize(R.styleable.ListTile_lt_extra_left_margin, 0);

                    ImageView extraImageView = new ImageView(context);
                    extraImageView.setScaleType(ScaleType.CENTER_CROP);
                    extraImageView.setImageDrawable(extraDrawable);
                    extraImageView.setLayoutParams(new LayoutParams(extraSize, extraSize));

                    extra = extraImageView;

                    addView(extra);
                }
            }
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

    @NonNull
    public ImageView getLeading() {
        return Objects.requireNonNull(leading, "Leading not set!");
    }

    @NonNull
    public ImageView getTrailing() {
        return Objects.requireNonNull(trailing, "Trailing not set!");
    }

    @NonNull
    public TextView getTitle() {
        return Objects.requireNonNull(title, "Title not set!");
    }

    @NonNull
    public TextView getSubtitle() {
        return Objects.requireNonNull(subtitle, "Subtitle not set!");
    }

    @NonNull
    public TextView getExtraText() {
        if (extraType == EXTRA_TYPE_TEXT) {
            return (TextView) Objects.requireNonNull(extra, "Extra not set!");
        } else {
            throw new IllegalStateException("Extra Text not set!");
        }
    }

    @NonNull
    public ImageView getExtraImage() {
        if (extraType == EXTRA_TYPE_IMAGE) {
            return (ImageView) Objects.requireNonNull(extra, "Extra not set!");
        } else {
            throw new IllegalStateException("Extra Image not set!");
        }
    }

    public void tintLeading(@ColorRes int color) {
        int tint = ContextCompat.getColor(getContext(), color);
        leading.setImageDrawable(tint(leading.getDrawable(), tint));
    }

    public void tintTrailing(@ColorRes int color) {
        int tint = ContextCompat.getColor(getContext(), color);
        trailing.setImageDrawable(tint(trailing.getDrawable(), tint));
    }

    /**
     * 当在代码中调用了可能改变组件大小的方法之后，需要调用该方法重新布局。例如TextView的setText()方法。
     * <p>
     * 注意：组件初始化setText()时不需要调用该方法。
     */
    public void refresh() {
        post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }
}
