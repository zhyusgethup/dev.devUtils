package cn.changeforyou.web.cloud.devUtilApi.db.sql;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

@Component
public class SqlFormatUtil {
    private static final Set<String> BEGIN_CLAUSES = new HashSet<String>();
    private static final Set<String> END_CLAUSES = new HashSet<String>();
    private static final Set<String> LOGICAL = new HashSet<String>();
    private static final Set<String> QUANTIFIERS = new HashSet<String>();
    private static final Set<String> DML = new HashSet<String>();
    private static final Set<String> MISC = new HashSet<String>();
    static final String indentString = "    ";
    static final String initial = "\n    ";

    public String format(String source) {
        return new FormatProcess(source).perform().trim();
    }

    public String formatDDL(String source) {
        return new FormatProcess(source, true).perform().trim();
    }

    static {
        BEGIN_CLAUSES.add("left");
        BEGIN_CLAUSES.add("right");
        BEGIN_CLAUSES.add("inner");
        BEGIN_CLAUSES.add("outer");
        BEGIN_CLAUSES.add("group");
        BEGIN_CLAUSES.add("order");

        END_CLAUSES.add("where");
        END_CLAUSES.add("set");
        END_CLAUSES.add("having");
        END_CLAUSES.add("join");
        END_CLAUSES.add("from");
        END_CLAUSES.add("by");
        END_CLAUSES.add("join");
        END_CLAUSES.add("into");
        END_CLAUSES.add("union");

        LOGICAL.add("and");
        LOGICAL.add("or");
        LOGICAL.add("when");
        LOGICAL.add("else");
        LOGICAL.add("end");

        QUANTIFIERS.add("in");
        QUANTIFIERS.add("all");
        QUANTIFIERS.add("exists");
        QUANTIFIERS.add("some");
        QUANTIFIERS.add("any");

        DML.add("insert");
        DML.add("update");
        DML.add("delete");

        MISC.add("select");
        MISC.add("on");
    }

    private static class FormatProcess {
        boolean ddlModel = false;
        boolean beginLine = true;
        boolean afterBeginBeforeEnd = false;
        boolean afterByOrSetOrFromOrSelect = false;
        boolean afterValues = false;
        boolean afterOn = false;
        boolean afterBetween = false;
        boolean afterInsert = false;
        int inFunction = 0;
        int parensSinceSelect = 0;
        private LinkedList<Integer> parenCounts = new LinkedList<Integer>();
        private LinkedList<Boolean> afterByOrFromOrSelects = new LinkedList<Boolean>();

        int indent = 1;

        StringBuffer result = new StringBuffer();
        StringTokenizer tokens;
        String lastToken;
        String token;
        String lcToken;

        public FormatProcess(String sql) {
            this.tokens = new StringTokenizer(sql, "()+*/-=<>'`\"[], \n\r\f\t", true);
        }

        public FormatProcess(String sql, boolean ddlModel) {
            this.tokens = new StringTokenizer(sql, "()+*/-=<>'`\"[], `;\n\r\f\t", true);
            this.ddlModel = ddlModel;
        }

        public String perform() {
            this.result.append("\n    ");

            while (this.tokens.hasMoreTokens()) {
                this.token = this.tokens.nextToken();
                this.lcToken = this.token.toLowerCase();
                if ("'".equals(this.token)) {
                    String t;
                    do {
                        t = this.tokens.nextToken();
                        this.token += t;
                    } while ((!"'".equals(t)) && (this.tokens.hasMoreTokens()));
                } else if ("\"".equals(this.token)) {
                    String t;
                    do {
                        t = this.tokens.nextToken();
                        this.token += t;
                    } while (!"\"".equals(t));
                } else if ("`".equals(this.token)) {
                    String t;
                    do {
                        t = this.tokens.nextToken();
                        this.token += t;
                    } while ((!"`".equals(t)) && this.tokens.hasMoreTokens());
                }

                if ((this.afterByOrSetOrFromOrSelect) && (",".equals(this.token))) {
                    commaAfterByOrFromOrSelect();
                } else if ((this.afterOn) && (",".equals(this.token))) {
                    commaAfterOn();
                } else if ("(".equals(this.token)) {
                    openParen();
                } else if (")".equals(this.token)) {
                    closeParen();
                } else if (SqlFormatUtil.BEGIN_CLAUSES.contains(this.lcToken)) {
                    beginNewClause();
                } else if (SqlFormatUtil.END_CLAUSES.contains(this.lcToken)) {
                    endNewClause();
                } else if ("select".equals(this.lcToken)) {
                    select();
                } else if (SqlFormatUtil.DML.contains(this.lcToken)) {
                    updateOrInsertOrDelete();
                } else if ("values".equals(this.lcToken)) {
                    values();
                } else if ("on".equals(this.lcToken)) {
                    on();
                } else if ((this.afterBetween) && (this.lcToken.equals("and"))) {
                    misc();
                    this.afterBetween = false;
                } else if (SqlFormatUtil.LOGICAL.contains(this.lcToken)) {
                    logical();
                } else if (isWhitespace(this.token)) {
                    white();
                } else if (ddlModel && ",".equals(this.token)) {
                    out();
                    newline();
                } else if (ddlModel && ";".equals(this.token)) {
                    out();
                    newlineWithoutIndent();
                } else {
                    misc();
                }

                if (!isWhitespace(this.token)) {
                    this.lastToken = this.lcToken;
                }
            }

            return this.result.toString();
        }

        private void commaAfterOn() {
            out();
            this.indent -= 1;
            newline();
            this.afterOn = false;
            this.afterByOrSetOrFromOrSelect = true;
        }

        private void commaAfterByOrFromOrSelect() {
            out();
            newline();
        }

        private void logical() {
            if ("end".equals(this.lcToken)) {
                this.indent -= 1;
            }
            newline();
            out();
            this.beginLine = false;
        }

        private void on() {
            this.indent += 1;
            this.afterOn = true;
            newline();
            out();
            this.beginLine = false;
        }

        private void misc() {
            out();
            if ("between".equals(this.lcToken)) {
                this.afterBetween = true;
            }
            if (this.afterInsert) {
                newline();
                this.afterInsert = false;
            } else {
                this.beginLine = false;
                if ("case".equals(this.lcToken))
                    this.indent += 1;
            }
        }

        private void white() {
            if (!this.beginLine)
                this.result.append(" ");
        }

        private void updateOrInsertOrDelete() {
            out();
            this.indent += 1;
            this.beginLine = false;
            if ("update".equals(this.lcToken)) {
                newline();
            }
            if ("insert".equals(this.lcToken))
                this.afterInsert = true;
        }

        private void select() {
            out();
            this.indent += 1;
            newline();
            this.parenCounts.addLast(new Integer(this.parensSinceSelect));
            this.afterByOrFromOrSelects.addLast(Boolean.valueOf(this.afterByOrSetOrFromOrSelect));
            this.parensSinceSelect = 0;
            this.afterByOrSetOrFromOrSelect = true;
        }

        private void out() {
            this.result.append(this.token);
        }

        private void endNewClause() {
            if (!this.afterBeginBeforeEnd) {
                this.indent -= 1;
                if (this.afterOn) {
                    this.indent -= 1;
                    this.afterOn = false;
                }
                newline();
            }
            out();
            if (!"union".equals(this.lcToken)) {
                this.indent += 1;
            }
            newline();
            this.afterBeginBeforeEnd = false;
            this.afterByOrSetOrFromOrSelect = (("by".equals(this.lcToken)) || ("set".equals(this.lcToken))
                    || ("from".equals(this.lcToken)));
        }

        private void beginNewClause() {
            if (!this.afterBeginBeforeEnd) {
                if (this.afterOn) {
                    this.indent -= 1;
                    this.afterOn = false;
                }
                this.indent -= 1;
                newline();
            }
            out();
            this.beginLine = false;
            this.afterBeginBeforeEnd = true;
        }

        private void values() {
            this.indent -= 1;
            newline();
            out();
            this.indent += 1;
            newline();
            this.afterValues = true;
        }

        private void closeParen() {
            this.parensSinceSelect -= 1;
            if (this.parensSinceSelect < 0) {
                this.indent -= 1;
                this.parensSinceSelect = ((Integer) this.parenCounts.removeLast()).intValue();
                this.afterByOrSetOrFromOrSelect = ((Boolean) this.afterByOrFromOrSelects.removeLast()).booleanValue();
            }
            if (this.inFunction > 0) {
                this.inFunction -= 1;
                out();
            } else {
                if (!this.afterByOrSetOrFromOrSelect) {
                    this.indent -= 1;
                    newline();
                }
                out();
            }
            this.beginLine = false;
        }

        private void openParen() {
            if ((isFunctionName(this.lastToken)) || (this.inFunction > 0)) {
                this.inFunction += 1;
            }
            this.beginLine = false;
            if (this.inFunction > 0) {
                out();
            } else {
                out();
                if (!this.afterByOrSetOrFromOrSelect) {
                    if (!ddlModel) {
                        this.indent += 1;
                    }
                    newline();
                    this.beginLine = true;
                }
            }
            this.parensSinceSelect += 1;
        }

        private static boolean isFunctionName(String tok) {
            char begin = tok.charAt(0);
            boolean isIdentifier = (Character.isJavaIdentifierStart(begin)) || ('"' == begin);
            return (isIdentifier) && (!SqlFormatUtil.LOGICAL.contains(tok))
                    && (!SqlFormatUtil.END_CLAUSES.contains(tok))
                    && (!SqlFormatUtil.QUANTIFIERS.contains(tok)) && (!SqlFormatUtil.DML.contains(tok))
                    && (!SqlFormatUtil.MISC.contains(tok));
        }

        private static boolean isWhitespace(String token) {
            return " \n\r\f\t".indexOf(token) >= 0;
        }

        private void newlineWithoutIndent() {
            this.result.append("\n");
            this.beginLine = true;
        }

        private void newline() {
            this.result.append("\n");
            for (int i = 0; i < this.indent; i++) {
                this.result.append("    ");
            }
            this.beginLine = true;
        }
    }
}
