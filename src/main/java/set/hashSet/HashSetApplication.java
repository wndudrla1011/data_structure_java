package set.hashSet;

public class HashSetApplication {

    public static void main(String[] args) {

        Custom a = new Custom("aaa", 10);
        Custom b = new Custom("aaa", 10);

        HashSet<Custom> set = new HashSet<>();

        set.add(a);
        if (set.add(b)) {
            System.out.println("hashset에 b객체가 추가됨");
        } else {
            System.out.println("중복원소!");
        }

    }

    static class Custom {

        String str;
        int val;

        public Custom(String str, int val) {
            this.str = str;
            this.val = val;
        }

        @Override
        public boolean equals(Object obj) {

            if ((obj instanceof Custom) && obj != null) {
                //주소가 같은 경우
                if (obj == this) {
                    return true;
                }
                Custom other = (Custom) obj;
                //str이 null인 경우 객체 비교 불가
                if (str == null) {
                    //둘 다 str이 null이면 val 비교
                    if (other.str == null) {
                        return other.val == val;
                    }
                    return false;
                }
                //str과 val 내용 동일
                if (other.str.equals(str) && other.val == val) {
                    return true;
                }
            }

            return false;

        }

        @Override
        public int hashCode() {

            final int prime = 31; //소수

            int result = 17;

            /*
            * 연산 과정 자체는 어떻게 해주어도 무방하지만
            * 최대한 중복되지 않도록
            * */
            result = prime * result * val;
            result = prime * result + (str != null ? str.hashCode() : 0);
            return result;

        }

    }

}
