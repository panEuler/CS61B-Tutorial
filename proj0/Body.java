public class Body {
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;

    public Body(double xP, double yP, double xV,
            double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;

    }

    public Body(Body b) {
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    public double calcDistance(Body b) {
        return Math.sqrt(Math.pow((b.xxPos - xxPos), 2) + Math.pow((b.yyPos - yyPos), 2));
    }

    public double calcForceExertedBy(Body b) {
        return (6.67e-11 * mass * b.mass) / Math.pow(calcDistance(b), 2);
    }

    public double calcForceExertedByX(Body b) {
        return (6.67e-11 * mass * b.mass * (b.xxPos - xxPos) / calcDistance(b)) / Math.pow(calcDistance(b), 2);
    }

    public double calcForceExertedByY(Body b) {
        return (6.67e-11 * mass * b.mass * (b.yyPos - yyPos) / calcDistance(b)) / Math.pow(calcDistance(b), 2);
    }

    public double calcNetForceExertedByX(Body[] bodies) {
        double netForceX = 0.0;
        for (Body b : bodies) {
            if (b != this) {
                netForceX = netForceX + calcForceExertedByX(b);
            }
        }
        return netForceX;
    }

    // 定义一个方法 calcNetForceExertedByY，用于计算在Y轴方向上受到的总净力
    public double calcNetForceExertedByY(Body[] bodies) {
        // 初始化 netForceY 变量为 0.0，用于累加在Y轴方向上受到的力
        double netForceY = 0.0;
        // 遍历传入的 Body 数组 bodies
        for (Body b : bodies) {
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
        double xxVel = this.xxVel + aX * time;
        double yyVel = this.yyVel + aY * time;
        xxPos = xxPos + xxVel * time;
        yyPos = yyPos + yyVel * time;
    }

    public void draw() {

        StdDraw.picture(xxPos, yyPos, imgFileName);

    }
}
