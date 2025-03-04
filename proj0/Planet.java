import javax.print.event.PrintJobAttributeListener;

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV,
            double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;

    }

    public Planet(Planet b) {
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    public double calcDistance(Planet b) {
        return Math.sqrt(Math.pow((b.xxPos - xxPos), 2) + Math.pow((b.yyPos - yyPos), 2));
    }

    public double calcForceExertedBy(Planet b) {
        return (6.67e-11 * mass * b.mass) / Math.pow(calcDistance(b), 2);
    }

    public double calcForceExertedByX(Planet b) {
        return (6.67e-11 * mass * b.mass * (b.xxPos - xxPos) / calcDistance(b)) / Math.pow(calcDistance(b), 2);
    }

    public double calcForceExertedByY(Planet b) {
        return (6.67e-11 * mass * b.mass * (b.yyPos - yyPos) / calcDistance(b)) / Math.pow(calcDistance(b), 2);
    }

    public double calcNetForceExertedByX(Planet[] bodies) {
        double netForceX = 0.0;
        for (Planet b : bodies) {
            if (b != this) {
                netForceX = netForceX + calcForceExertedByX(b);
            }
        }
        return netForceX;
    }

    // 定义一个方法 calcNetForceExertedByY，用于计算在Y轴方向上受到的总净力
    public double calcNetForceExertedByY(Planet[] bodies) {
        // 初始化 netForceY 变量为 0.0，用于累加在Y轴方向上受到的力
        double netForceY = 0.0;
        // 遍历传入的 Body 数组 bodies
        for (Planet b : bodies) {
            // 检查当前遍历的 Body 是否不是自身（避免自己对自己施加力）
            if (b != this) {
                // 调用 calcForceExertedByY 方法计算当前 Body 在Y轴方向上对自身的力，并累加到 netForceY
                netForceY = netForceY + calcForceExertedByY(b);
            }
        }
        // 返回计算得到的在Y轴方向上的总净力
        return netForceY;
    }

    public void update(double time, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        xxVel = this.xxVel + aX * time;
        yyVel = this.yyVel + aY * time;
        xxPos = xxPos + xxVel * time;
        yyPos = yyPos + yyVel * time;
    }

    public void draw() {

        StdDraw.picture(xxPos, yyPos, imgFileName);

    }
}
